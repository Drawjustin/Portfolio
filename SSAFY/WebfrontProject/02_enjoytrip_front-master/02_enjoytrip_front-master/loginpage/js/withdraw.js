var myModal = new bootstrap.Modal(document.getElementById("withdrawalModal"));

function withdrawal() {
  let info = JSON.parse(localStorage.getItem("user"));
  let password = document.getElementById("withdrawalPassword").value;

  console.log(info);
  console.log(password);
  if (!info) {
    myModal.hide();
    alert("실패");
  } else {
    if (password == info.password) {
      localStorage.removeItem("user");
      localStorage.removeItem("loginStatus");
      location.replace("../mainpage/main_page.html");
    } else {
      alert("실패");
    }
  }
}
