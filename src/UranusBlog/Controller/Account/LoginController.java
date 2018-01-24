package UranusBlog.Controller.Account;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        PrintWriter out = resp.getWriter();
//        out.println("hello login");
     //   super.doGet(req,resp);
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        //Testing-> switch back
        String userName= req.getParameter("userName");
        //String userName= "user7";
        String password= req.getParameter("password");
        //String password= "password7";

        PrintWriter out = resp.getWriter();
        boolean authorized=false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



        Properties dbProps = new Properties();
        dbProps.setProperty("url", "jdbc:mysql://db.sporadic.nz/xliu617");
        dbProps.setProperty("user", "xliu617");
        dbProps.setProperty("password", "123");
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM account WHERE username = ? AND password = ?;")) {
                stmt.setString(1, userName);
                stmt.setString(2, password);
                try (ResultSet r = stmt.executeQuery()) {
                    if (r != null){
                        authorized=false;
                    }
                    while (r.next()){
                        String requestUserName= r.getString (2);
                        String requestPassword= r.getString (3);

                        if (requestUserName.equals(userName)&&requestPassword.equals(password)){
                            authorized=true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (authorized){
            out.println("<p> Login successfully! </p>");
        }
        if (!authorized){
            out.println("<p> Go away!! </p>");
        }
    }
}