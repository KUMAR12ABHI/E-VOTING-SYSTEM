/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.CandidateDao;
import evoting.dao.CandidateDetails;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Soft2
 */
public class ShowCandidateControllerServlet extends HttpServlet {

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
       RequestDispatcher rd=null;
       String data=(String)request.getParameter("data");
        System.out.println(data);
       HttpSession session=request.getSession();
       String userid=(String)session.getAttribute("userid");
       if(userid==null)
       {
           session.invalidate();
           response.sendRedirect("accessdenied.html");
           return;
       }
       try
       {
           if(data!=null&&data.equals("cid"))
           {
               ArrayList<String> candidateId=CandidateDao.getCandidateId();
               request.setAttribute("candidateId", candidateId);
               request.setAttribute("result", "candidateList");
           }
           else{
               CandidateDetails candidate=CandidateDao.getDetailsById(data);
               request.setAttribute("result", "details");
               request.setAttribute("candidate", candidate);
           }
           rd=request.getRequestDispatcher("adminshowcandidate.jsp");
       }
       catch(Exception e)
       {
           request.setAttribute("exception", e);
           rd=request.getRequestDispatcher("showexception.jsp");
           e.printStackTrace();
       }
       finally
       {
           if(rd!=null)
           rd.forward(request, response);
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
