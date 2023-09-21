// 기본 로직

// 시도 데이터 가져오기
function init() {
  let sidoCode;
  let sigunguCode;
  fetch(
    "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=LDb%2BWDXaYVdtanKBTtb3rvL54cNrzSD1WAaUJSZRHeBvKg6w2oxnpLo3JGbDIvLBzsu49%2BZcdP4QHOjuwzCixQ%3D%3D&numOfRows=100&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json"
  )
    .then(function (response) {
      return response.json();
    })
    .then(function (data) {
      sidoCode = data.response.body.items.item;
      localStorage.setItem("sidoCode", JSON.stringify(data.response.body.items.item));
    })
    .then(() => {
      Promise.all(
        sidoCode.map(async (element) => {
          const response = await fetch(
            `https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=LDb%2BWDXaYVdtanKBTtb3rvL54cNrzSD1WAaUJSZRHeBvKg6w2oxnpLo3JGbDIvLBzsu49%2BZcdP4QHOjuwzCixQ%3D%3D&numOfRows=100&pageNo=1&MobileOS=ETC&MobileApp=AppTest&areaCode=${element.code}&_type=json`
          );
          const data = await response.json();
          return data.response.body.items.item;
        })
      )
        .then((resultArray) => {
          // 모든 fetch 요청이 완료된 후 resultArray에 데이터가 저장됩니다.
          sigunguCode = resultArray;
          localStorage.setItem("sigunguCode", JSON.stringify(resultArray));
        })
        .catch((error) => {
          console.error(error); // 에러 처리
        });
    })
    .then(() => {
      var sidoSelect = document.getElementById("sidoSelect");
      var sigunguSelect = document.getElementById("sigunguSelect");

      sidoCode.map((element) => {
        let option = document.createElement("option");
        option.value = element.code;
        option.innerText = element.name;
        sidoSelect.appendChild(option);
      });

      // 시도 선택시 시군구 옵션 설정하기
      sidoSelect.addEventListener("change", (event) => {
        let index = event.target.selectedIndex;
        let selectedSigunguCode = sigunguCode[index - 1];
        sigunguSelect.innerHTML = '<option value="-1">시군구</option>';
        if (selectedSigunguCode != 0) {
          selectedSigunguCode.map(async (element) => {
            let option = document.createElement("option");
            option.value = element.code;
            option.innerText = element.name;
            sigunguSelect.appendChild(option);
          });
        }
      });
    });
}

//초기화
init();

// 지도 설정
var mapContainer = document.getElementById("map"), // 지도를 표시할 div
  mapOption = {
    center: new kakao.maps.LatLng(36.38, 127.51), // 지도의 중심좌표
    level: 12, // 지도의 확대 레벨
  };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

var clusterer = new kakao.maps.MarkerClusterer({
  map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
  averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
  minLevel: 10, // 클러스터 할 최소 지도 레벨
});

// 조회하기 버튼 핸들러 설정
document.getElementById("searchBtn").addEventListener("click", searchEventHandler);

// 함수
function logout() {
  localStorage.removeItem("loginStatus");
  location.replace("../mainpage/main_page.html");
}

// 데이터 가져오기
async function getData() {
  let checkedOptions = document.getElementsByClassName("checkbox");

  let selectedSidoCode = sidoSelect.options[sidoSelect.selectedIndex].value;
  let selectedSigunguCode = sigunguSelect.options[sigunguSelect.selectedIndex].value;

  let sidoOption = parseInt(selectedSidoCode) >= 0 ? `&areaCode=${selectedSidoCode}` : "";
  let sigunguOption =
    parseInt(selectedSigunguCode) >= 0 ? `&sigunguCode=${selectedSigunguCode}` : "";

  let result = await Promise.all(
    Array.from(checkedOptions)
      .filter((option) => option.checked)
      .map(async (option) => {
        let typeOption = option.checked ? `&contentTypeId=${option.value}` : "";

        let savedData = localStorage.getItem("data_" + sidoOption + sigunguOption + typeOption);

        if (savedData) {
          return JSON.parse(savedData);
        } else {
          const response = await fetch(
            `https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=LDb%2BWDXaYVdtanKBTtb3rvL54cNrzSD1WAaUJSZRHeBvKg6w2oxnpLo3JGbDIvLBzsu49%2BZcdP4QHOjuwzCixQ%3D%3D&numOfRows=10000&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A${sidoOption}${sigunguOption}${typeOption}`
          );

          const data = await response.json();
          // localStorage.setItem(
          //   "data_" + sidoOption + sigunguOption + typeOption,
          //   JSON.stringify(data.response.body.items.item)
          // );
          return data.response.body.items.item;
        }
      })
  )
    .then((resultArray) => {
      return resultArray.flat();
    })
    .catch((error) => {
      console.error(error); // 에러 처리
    });

  return result;
}
// 마커 생성하기
async function setMarkers(result) {
  console.log("setMarker");
  await clusterer.clear();

  result.forEach((element) => {
    var position = new kakao.maps.LatLng(parseFloat(element.mapy), parseFloat(element.mapx));
    var marker = new kakao.maps.Marker({
      map: map,
      position: position, // 마커를 표시할 위치
      title: element.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      clickable: true,
    });

    clusterer.addMarker(marker);

    var content = `<div class="container m-3">
      <div class="row">
          <div class="col-md-6 m-auto">
              <div class="card">
                  <img src="${element.firstimage}" class="card-img-top" alt="장소 이미지">
                  <div class="card-body">
                      <h5 class="card-title">${element.title}</h5>
                      <p class="card-text">${element.addr1}</p>
                  </div>
              </div>
          </div>
      </div>
  </div>`;

    var infowindow = new kakao.maps.InfoWindow({
      content: content,
      removable: true,
    });

    kakao.maps.event.addListener(marker, "click", function () {
      // 마커 위에 인포윈도우를 표시합니다
      infowindow.open(map, marker);
    });
  });

  clusterer.redraw();

  var moveLatLon = new kakao.maps.LatLng(36.38, 127.51);
  map.setCenter(moveLatLon);
  map.setLevel(12);
}

// 조회하기 버튼 클릭 핸들러
async function searchEventHandler() {
  let result = await getData();
  await setMarkers(result);
  var tr = `<table class="table table-hover text-nowrap">
  <thead>
    <tr>
      <th scope="col">장소 이름 및 위치</th>
      <th scope="col">장소 사진</th>
      <th scope="col">장소 전화번호</th>
    </tr>
  </thead> 
  <tbody>`;
  result.forEach((element) => {
    var tel = "000-0000-0000";
    if (element.tel != null) tel = element.tel;
    if (tel == "") tel = "전화번호 없음";
    tr += `<tr scope = "row" style="height: 200px;">
    <td class="w-25 p-3">${element.title}<br>${element.addr1} ${element.addr2}</td>
    <td class="w-25 p-3"><img src = "${element.firstimage}" height = 200px width = 300px></td>
    <td class="w-25 p-3">${tel}</td>
  </tr>`;
  });
  tr += ` </tbody>`;
  document.getElementById("secondtb").innerHTML = tr;
}
