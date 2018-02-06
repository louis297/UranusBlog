package UranusBlog.Controller.Account;

import UranusBlog.DAO.AccountDAO;
import UranusBlog.DB.MySQLDatabase;
import UranusBlog.Model.Account;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DeleteAccountController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userid = Integer.parseInt(req.getParameter("userid"));
        System.out.println(userid);
        boolean success = false;
        PrintWriter out = resp.getWriter();

        try (AccountDAO dao = new AccountDAO(new MySQLDatabase(getServletContext()))) {
            dao.deleteUser(userid);
//          need to edit the procedure written in AccountDao. Calling the wrong stored procedure
            out.println("<p> user: "+userid+" is successfully deleted </p> <button  onclick=\"window.history.back()\"> Go Back to Previous Page </button>");
        } catch (Exception e) {
            out.print("<p> An Error Has Occurred. User cannot be deleted </p> <button  onclick=\"window.history.back()\"> Go Back to Previous Page </button>");
            e.printStackTrace();
        }

    }
}
