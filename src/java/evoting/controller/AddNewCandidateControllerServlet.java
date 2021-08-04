/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.CandidateDao;
import evoting.dto.CandidateDto;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author ABHISHEK
 */
public class AddNewCandidateControllerServlet extends HttpServlet {

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
        try
        {
         ServletFileUpload sfu=new ServletFileUpload(new DiskFileItemFactory());
        List<FileItem> multiparts = sfu.parseRequest(new ServletRequestContext(request));	
        ArrayList<String> objValues=new ArrayList<>();
        InputStream fileContent=null;
         for(FileItem item : multiparts){
		if (item.isFormField()) {
               
                String fieldName = item.getFieldName();
                String fieldValue = item.getString();
                    System.out.println("Inside If");
                    System.out.println(fieldName+":"+fieldValue);
                    objValues.add(fieldValue);
                
            }
            else {
                
                String fieldName = item.getFieldName();
                String fileName=item.getName();
               // objValues.add(fileName);
                System.out.println(fieldName+":"+fileName);
                 fileContent = item.getInputStream();
                   // System.out.println(item.getSize());
                    System.out.println("Inside Else");
                System.out.println("content:"+fileContent);
                System.out.println(fileName);
            } 
                System.out.println("ArrayList value:"+objValues);
		}
         System.out.println("Cheking 1");
           CandidateDto candidate1=new CandidateDto(objValues.get(0),objValues.get(3),objValues.get(4),objValues.get(1),fileContent);
            System.out.println("Checking 2");
            boolean result=false;
          result=CandidateDao.addCandidate(candidate1);
            System.out.println("Add new candidate controler result: "+result);
             if(result==true)
              {
             rd=request.getRequestDispatcher("success.jsp");
               }
         else
            {
             rd=request.getRequestDispatcher("failure.jsp");
             }
        }
        catch(Exception e)
        {
            System.out.println("Exception occured"+e);
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
