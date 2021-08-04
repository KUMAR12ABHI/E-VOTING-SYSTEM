/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.CandidateDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author Soft2
 */
public class AddCandidateControllerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session=request.getSession();
        String userid=(String)session.getAttribute("userid");
        String candidate=(String)request.getParameter("id");
        String usid=(String)request.getParameter("uid");
        if(userid==null)
        {
            session.invalidate();
            response.sendRedirect("accessdenied.html");
            return;
        }
        else if(candidate!=null&&candidate.equals("getid"))
        {
            try
            {
                String cid=CandidateDao.getNewCandidateId();
                out.println(cid);
                return;
            }
            catch(SQLException e)
            {
                RequestDispatcher rd=request.getRequestDispatcher("showexception.jsp");
                request.setAttribute("Exception",e);
                rd.forward(request, response);
                e.printStackTrace();
            }
        }
        else if(usid!=null)
        {
            try
            {
            String username=CandidateDao.getUsernameById(usid);
            ArrayList<String> city=CandidateDao.getCity();
            JSONObject json=new JSONObject();
            StringBuffer displayBlock=new StringBuffer("");
            for(String c:city)
                displayBlock.append((char)34+"<option value='"+c+"'>"+c+"</option>");
            displayBlock.append((char)34+"");
                System.out.println(displayBlock);
                System.out.println(username);
                if(username==null)
                    username="wrong";
                System.out.println("User Name:"+username);
            json.put("username", username);
            json.put("city", displayBlock.toString());
            out.println(json);
            }
            catch(Exception e)
            {
                RequestDispatcher rd=request.getRequestDispatcher("showexception.jsp");
                request.setAttribute("Exception",e);
                rd.forward(request, response);
                e.printStackTrace();
            }
        }
        
            
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
