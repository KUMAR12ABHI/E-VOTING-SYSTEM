<%
    String userid=(String)request.getAttribute("userid");
    String result=(String)request.getAttribute("result");
    System.out.println(userid+" "+result);
    if(userid!=null&&result!=null)
    {
        HttpSession sess=request.getSession();
        sess.setAttribute("userid", userid);
        if(result.equalsIgnoreCase("Admin"))
        {
        String url="AdminControllerServlet;jsessionid="+session.getId();
        out.println(url);
        }
        else
        {
        String url="VotingControllerServlet;jsessionid="+session.getId();
        out.println(url);
        }
    }
    else
    {
        out.println("error");
    }
%>