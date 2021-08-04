package evoting.controller;

import evoting.dao.UserDao;
import evoting.dto.UserDto;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginControllerServlet")
public class LoginControllerServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd=null;
        String userid=request.getParameter("userid");
        String password=request.getParameter("password");
        String logout=(String)request.getParameter("logout");
        if(logout!=null)
        {
            HttpSession session=request.getSession();
            session.invalidate();
            response.sendRedirect("login.html");
            return;
        }   
        System.out.println("Login Controller Servlet");
        UserDto user=new UserDto(userid,password);
        try
        {
            String result=UserDao.validateUser(user);
            request.setAttribute("result", result);
            request.setAttribute("userid", userid);
            rd=request.getRequestDispatcher("loginresponse.jsp");            
        }
        catch(Exception e)
        {
            request.setAttribute("exception", e);
            rd=request.getRequestDispatcher("showexception.jsp");
            e.printStackTrace();
        }
        finally
        {
            rd.forward(request, response);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
}
