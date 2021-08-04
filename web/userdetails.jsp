<%@page import="evoting.dto.UserDetails"%>
<%@page import="java.util.ArrayList"%>
<%
     String userid=(String)session.getAttribute("userid");
    if(userid==null)
            {
                response.sendRedirect("accessdenied.html");
                return;
            }
    String result=(String)request.getAttribute("result");
    StringBuffer displayBlock=new StringBuffer("");
    System.out.println(result+" in userdetails.jsp");
    if(result.equals("userid"))
    {
        ArrayList<String> id=(ArrayList)request.getAttribute("usersId");
        for(String u:id)
        {
            displayBlock.append((char)34+"<option value='"+u+"'>"+u+"</option>");
        }
        displayBlock.append((char)34);
        out.println(displayBlock);
    }
    else if(result.equals("details"))
    {
        UserDetails user=(UserDetails)request.getAttribute("userdetails");
        displayBlock.append("<table><tr><th>Username:</th><td>"+user.getUsername()+"</td></tr>");
        displayBlock.append("<tr><th>Email:</th><td>"+user.getEmail()+"</td></tr>");
        displayBlock.append("<tr><th>Mobile No:</th><td>"+user.getMobile()+"</td></tr>");
        displayBlock.append("<tr><th>Address:</th><td>"+user.getAddress()+"</td></tr>");
        displayBlock.append("<tr><th>City:</th><td>"+user.getCity()+"</td></tr>");
        out.println(displayBlock);
    }
%>