var username,password,cpassword,city,address,adhar,email,mobile;
function addUser() {
    username=$("#username").val();
    password=$("#password").val();
    cpassword=$("#cpassword").val();
    city=$("#city").val();
    address=$("#address").val();
    adhar=$("#adhar").val();
    email=$("#email").val();
    mobile=$("#mobile").val();
    if(validateUser())
    {
        if(password !== cpassword)
        {
            swal("Error!", "password does not match!", "error");
            //$("#result").text("password doent match!!").css("color","red");
            //window.setTimeout(function(){$("#result").fadeOut('slow');}, 3000);
            return;
        }
        else
        {
            if(checkEmail(email)==false)
                return;
            if(validateAdhar(adhar)===false)
            {
                 swal("Error!", "Please Correct enter Adhar!", "error");
                 return;
            }
            var data={ username: username,
            password: password,
            city: city,
            address: address,
            userid: adhar,
            email: email,
            mobile: mobile};
            $.post("RegistrationControllerServlet",data ,processresponse);
        }
    }
}
function processresponse(responseText)
{
    responseText=responseText.trim();
    if(responseText==="success"){
        swal("success!", "Registration Successful!You can now Login", "success");
    }
    else if(responseText==="uap")
    {
        swal("Error!", "Sorry! the userid is already present!", "error");
    }
    else
    {
        swal("Error","Registration Failed ! Tray again","error");
    }
}


function validateUser() {
    if (username === "" || password === "" || cpassword === "" || city === "" || address === "" || adhar === "" || email === "" || mobile === "") {
        swal("Error!", "All fields are mandatory!", "error");
//        var str=$("#result");
//        str.text("All fields are mandatory").css("color","red");
//         window.setTimeout(function(){$("#result").fadeOut('slow');},3000);
        return false;
    } else {
        return true;
    }
}
function checkEmail(email)
{
var atposition=email.indexOf("@");  
var dotposition=email.lastIndexOf(".");  
if (atposition<1 || dotposition<atposition+2 || dotposition+2>=email.length){ 
    swal("Error!", "please enter valid email!", "error");
//  $("#result").text("please enter valid email!!").css("color","red"); 
//   window.setTimeout(function(){$("#result").fadeOut('slow');}, 3000);
  return false;  
  }  
  return true;
}
function validateAdhar(adhar)
{
    if(adhar.length<12)
        return false
    else
        return true;
}
function redirectUser()
{
    window.location="login.html";
}
function redirectRegistration()
{
    window.location="registration.html";
}