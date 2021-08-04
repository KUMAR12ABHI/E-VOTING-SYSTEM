var userid,password;
function connectUser()
{
    userid=$("#username").val();
    password=$("#password").val();
    if(validate()==false)
    {
        swal("Error!", "please enter userid/password!", "error");
        return;
    }
    var data={
        userid:userid,
        password:password
    }
     $.post("LoginControllerServlet",data ,processResponse);
}
function processResponse(responseText)
{
    responseText=responseText.trim();
    if(responseText==='error')
    {
        swal("Error!", "please enter valid userid/password!", "error");
    }
    else if(responseText.indexOf("jsessionid")!==-1)
    {
        swal("success!", "Login Accepted!", "success").then((value)=>{
            window.location=responseText;
        });
    }
    else 
    {
        swal("Error!", "An error occured during your request!", "error");
    }
}
function validate()
{
    if(userid===""||password==="")
        return false;
    return true;
}

