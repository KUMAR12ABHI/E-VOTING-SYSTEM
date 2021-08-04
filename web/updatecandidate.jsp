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
    StringBuffer displayBlock=new StringBuffer("");
    if(result.equals("cid"))
    {
        ArrayList<String> id=(ArrayList)request.getAttribute("id");
         for(String s:id)
                {
                    displayBlock.append((char)34+"<option value='"+s+"'>"+s+"</option>");
                }
                displayBlock.append((char)34+"");
                out.println(displayBlock);
    }
    else if(result.equals("details"))
    {
        ArrayList<String> city=(ArrayList)request.getAttribute("city");
        CandidateDetails candidate=(CandidateDetails)request.getAttribute("candidate");
        for(String s:city)
         {
            displayBlock.append((char)34+"<option value='"+s+"'>"+s+"</option>");
         }
            displayBlock.append((char)34+"");
            JSONObject json=new JSONObject();
            json.put("city", displayBlock);
            json.put("username", candidate.getCandidateName());
            json.put("userid", candidate.getUserId());
            String str=(char)34+"<img src='data:image/jpg;base64,"+candidate.getSymbol()+"' style='width:300px;height:200px;'/>"+(char)34;
            json.put("image", str);
            json.put("candidateid", candidate.getCandidateId());
            json.put("party", candidate.getParty());
            out.println(json);       
    }
%>