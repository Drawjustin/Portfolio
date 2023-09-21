function login() {
  let userinfo = JSON.parse(localStorage.getItem("user"));
  let id = document.getElementById("id").value;
  let password = document.getElementById("password").value;
  
  if (!id || !password) {
    alert('정보를 전부 입력해주세요');
    return false;
  }
  if (userinfo == null) {
    alert('로그인 정보를 확인해주세요');
    return false;
  }

  let storedEmail = userinfo.id;
  let storedPassword = userinfo.password;
  if (!storedEmail) {
    alert("회원가입을 먼저 해주세요");
    return false;
  }


  let isLasting = document.getElementById("lastingLoginCheckbox").checked;

  if (id == storedEmail && storedPassword == password) {
    if (isLasting) {
      localStorage.setItem("loginStatus", "true");
    }
    return true;
  } else {
    alert("로그인 정보를 다시 확인해 주세요");
    console.log("실패");
    return false;
  }
}

function gologin() {
  location.replace("./register_page.html");
}

function checked(){
  let email = document.getElementById("email").value;
  let id = document.getElementById("id").value;
  let name = document.getElementById("name").value;
  if (!id || !email || !name ) {
    alert('정보를 전부 입력해주세요');
    return false;
  }
  let userinfo = JSON.parse(localStorage.getItem("user"));
  if(!userinfo){
    alert('저장된 계정이 없습니다 회원가입을 먼저 해주세요');
    return false;
  }
  
  let storedEmail = userinfo.email;
  let storedname = userinfo.name;
  let storedid = userinfo.id;

  if(!ValidateEmail(email)){
      return false;
  }
  

  let storedPassword = userinfo.password;


  if (storedEmail != email || storedid != id || storedname != name) {
    alert("회원 정보가 틀렸습니다 다시입력해주세요");
    return false;
  }
  else{
    alert("회원님의 비밀번호는"+storedPassword+"입니다");
  }

}

window.onload = function () {
  if (localStorage.getItem("loginStatus") == "true") {
    location.replace("../mappage/map_page.html");
  }
};
function ValidateEmail(input) {

    var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
  
    if (input.match(validRegex)) {
  
      alert("Valid email address!");
  
  
      return true;
  
    } else {
  
      alert("Invalid email address!");
      return false;
  
    }
  
  }
  function ValidateEmail(input) {

    var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
  
    if (input.match(validRegex)) {
  
      alert("Valid email address!");
  
  
      return true;
  
    } else {
  
      alert("Invalid email address!");
      return false;
  
    }
  
  }