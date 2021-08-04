<%@page import="org.json.JSONObject"%>
<%@page import="evoting.dao.CandidateDetails"%>
<%@page import="java.util.ArrayList"%>
<%
    String userid=(String)session.getAttribute("userid");
    if(userid==null)
    {
        response.sendRedirect("accessdenied.html");
        return;
    }
    String result=(String)request.getAttribute("result");
    System.out.println(result);
    StringBuffer displayBlock=new StringBuffer("");
    if(result.equals("candidateList"))
    {
        ArrayList<String> candidateId=(ArrayList)request.getAttribute("candidateId");
        //displayBlock.append("<option>Chose Id</option>");
        for(String c:candidateId)
        {
            displayBlock.append((char)34+"<option value='"+c+"'>"+c+"</option>");
        }
        displayBlock.append((char)34);
        out.println(displayBlock);
    }
    else if(result.equals("details"))
    {
        CandidateDetails candidate=(CandidateDetails)request.getAttribute("candidate");
        String str="<img src='data:image/jpg;base64,"+candidate.getSymbol()+"' style='width:300px;height:200px;'/>";
        displayBlock.append((char)34+"<table>"
                            +"<tr><th>User Id:</th><td>"+candidate.getUserId()+"</td></tr>"
                            +"<tr><th>Candidate Name:</th><td>"+candidate.getCandidateName()+"</td></tr>"
                            +"<tr><th>City:</th><td>"+candidate.getCity()+"</td></tr>"
                            +"<tr><th>Party:</th><td>"+candidate.getParty()+"</td></tr>"
                            +"<tr><th>Symbol:</th><td id='image'></td></tr>"
                            +"</table>"+(char)34);
        System.out.println(displayBlock);
        JSONObject json=new JSONObject();
        json.put("image", str);
        json.put("subdetails", displayBlock);
        out.println(json);
    }
    System.out.println("in admin show candidate");
    System.out.println(displayBlock);
%>