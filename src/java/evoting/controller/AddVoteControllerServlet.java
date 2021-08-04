/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.UserDao;
import evoting.dao.VoteDao;
import evoting.dto.CandidateDto;
import evoting.dto.CandidateInfo;
import evoting.dto.VoteDto;
import java.io.IOException;
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
public class AddVoteControllerServlet extends HttpServlet {

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
       HttpSession session=request.getSession();
       String userid=(String)session.getAttribute("userid");
       String candidateId=(String)request.getParameter("candidateid");
        System.out.println("Add vote controller servlet");
       if(userid==null)
       {
           session.invalidate();
           response.sendRedirect("accessdenied.html");
           return;
       }
       else
       {
           try
           {
               System.out.println(candidateId+" "+userid);
               VoteDto vote=new VoteDto(candidateId,userid);
               boolean result=VoteDao.addVote(vote);
               CandidateInfo candidate=VoteDao.getVote(candidateId);
               System.out.println("candidate:");
               System.out.println(candidate);
               if(result==true)
                   session.setAttribute("candidate",candidate);
              // request.setAttribute("candidate", candidate);
               request.setAttribute("result", result);
               rd=request.getRequestDispatcher("verifyvote.jsp");               
           }
           catch(Exception e)
           {
               e.printStackTrace();
               request.setAttribute("exception", e);
               rd=request.getRequestDispatcher("showexception.jsp");
           }
           finally
           {
               rd.forward(request, response);
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
