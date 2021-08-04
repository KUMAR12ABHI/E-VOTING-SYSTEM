
<%@page contentType="text/html" pageEncoding="UTF-8" import="evoting.dto.CandidateInfo"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="stylesheet/backgroundimage.css" type="text/css" rel="stylesheet">
        <link href="stylesheet/showcandidate.css" rel="stylesheet">
        <link href="stylesheet/pageheader.css" rel="stylesheet">
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <title>Voting Response</title>
    </head>
    <body>
        <%
            String userid=(String)session.getAttribute("userid");
          // String cid=VoteDao.getCandidateId(userid);
          
           CandidateInfo candidate=(CandidateInfo)session.getAttribute("candidate");
            System.out.println("voting rsponse cid is :"+candidate);
           StringBuffer displayBlock=new StringBuffer("");
           displayBlock.append("<div class='sticky'><div class='candidate'>VOTE FOR CHANGE</div><br> ");
            if(userid==null)
            {
                session.invalidate();
                response.sendRedirect("accessdenied.jsp");
                return;
            }
            else
            {
                if(candidate==null)
                {
                    displayBlock.append("<br><div class='subcandidate'>Sorry your Vote is not added!</div>");
                    displayBlock.append("<div class='logout'><a href='logout.html'>logout</a></div></div>");
                    displayBlock.append("<div><h4 id='logout'><a href='LoginControllerServlet?logout=logout'>Logout</a></h4></div>");
                    out.println(displayBlock);
                }
                else
                {
                    System.out.println(candidate);
                    displayBlock.append("<div class='subcandidate' >Thank you for Voting!!</div><br>");
                    displayBlock.append("<div class='candidateprofile'>Your Vote Added Successfully!!</div>");
                    displayBlock.append("<div class='candidateprofile' id='"+candidate.getCandidateId()+"'>"
                            + "<strong>You are Voted to :</strong><br/><img src='data:image/jpg;base64,"
                 +candidate.getSymbol()+"' style='width:200px;height:200px;'/><br/><div class='candidateprofile'><p>Candidate Id:"
                 +candidate.getCandidateId()+"<br/>"
                 +"Candidate Name:"+candidate.getCandidateName()+"<br/>"
                 + " Party:"+candidate.getParty()+"</div><br/></div>");
                    displayBlock.append("<h2 id='logout'><a href='LoginControllerServlet?logout=logout'>Logout</a></h2>");
                }
                out.println(displayBlock);
            }
        %>
    </body>
</html>
