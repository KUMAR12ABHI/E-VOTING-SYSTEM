<%@page import="evoting.dto.UserDetails"%>
<%@page import="java.util.ArrayList"%>
<%
    String userid=(String)session.getAttribute("userid");
    if(userid==null)
            {
                response.sendRedirect("accessdenied.html");
                return;
            }
    ArrayList<UserDetails> users=(ArrayList)request.getAttribute("users");
    StringBuffer displayBlock=new StringBuffer("<table border='1'><tr><th>User Id</th><th>Username</th><th>Address</th><td>City</th><th>Email</th><th>Mobile No</th></tr>");
    for(UserDetails u:users)
    {
        displayBlock.append("<tr><td>"+u.getUserid()+"</td><td>"+u.getUsername()
                +"</td><td>"+u.getAddress()+"</td><td>"+u.getCity()
                +"</td><td>"+u.getEmail()+"</td><td>"+u.getMobile()+"</td></tr>");
    }
    displayBlock.append("</table>");
    out.println(displayBlock);
%>