package evoting.controller;

import evoting.dao.RegistrationDao;
import evoting.dao.UserDao;
import evoting.dto.UserDetails;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "RegistrationControllerServlet")
public class RegistrationControllerServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd=null;
        UserDetails user=new UserDetails();
        user.setUserid(request.getParameter("userid"));
        user.setPassword(request.getParameter("password"));
        user.setAddress(request.getParameter("address"));
        user.setCity(request.getParameter("city"));
        user.setEmail(request.getParameter("email" ));
        user.setMobile(Long.parseLong(request.getParameter("mobile")));
        user.setUsername(request.getParameter("username"));
        System.out.println(user);
        try {
            boolean result=false,userfound=false;
            if(!RegistrationDao.serachUser(user.getUserid()))
            {
            result=UserDao.addUser(user);
            }
            else
            {
                userfound=true;
            }
            request.setAttribute("result", result);
            request.setAttribute("userfound", userfound);
            request.setAttribute("username", user.getUsername());
            rd=request.getRequestDispatcher("registrationresponse.jsp");
        } catch (SQLException e) {
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
