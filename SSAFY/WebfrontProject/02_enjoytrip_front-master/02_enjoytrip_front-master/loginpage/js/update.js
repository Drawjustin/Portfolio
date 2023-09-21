function register(){
    localStorage.removeItem('user');
    var id = document.getElementById('form1Example3').value;
    var password = document.getElementById('form1Example2').value;
    var email = document.getElementById('form1Example1').value;
    var name = document.getElementById('form1Example4').value;
    var age = document.getElementById('form1Example5').value;

    if (!id || !password || !email || !name || !age || ValidateEmail(email)) {
        return false;
    }
    else{
    var jsonstring = {
        'id': id,
        'password' : password,
        'email' : email,
        'name' : name,
        'age': age
    };
    var string = JSON.stringify(jsonstring);


    localStorage.setItem('user', string);
    return true;
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