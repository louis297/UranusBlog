package UranusBlog.Controller.Account;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AccountEditController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        //
        //List<String> requestData = new ArrayList<>();

//        String userName = req.getParameter("username");
//        String firstName = req.getParameter("fname");
//        String lastName = req.getParameter("lname");
//        String middleName = req.getParameter("mname");
//        String password = req.getParameter("password");
//        String email = req.getParameter("email");
//        //https://stackoverflow.com/questions/6978631/how-to-set-date-format-in-html-date-input-tag
//        String dob = req.getParameter("birthday");
//        String nation = req.getParameter("nation");
//        String avitarPath = req.getParameter("avatarpath");
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        Properties dbProps = new Properties();
//        dbProps.setProperty("url", "jdbc:mysql://db.sporadic.nz/xliu617");
////      dbProps.setProperty("useSSL", "false");
//        dbProps.setProperty("user", "xliu617");
//        dbProps.setProperty("password", "123");
//        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
//            try (Statement stmt = conn.createStatement()) {
//                if (userName!=null && userName!=""){
//                    stmt.executeUpdate("INSERT INTO table account (2, 'Rob');");
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }
