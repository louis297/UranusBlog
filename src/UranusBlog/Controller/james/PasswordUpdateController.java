package UranusBlog.Controller.james;

import sun.net.smtp.SmtpClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class PasswordUpdateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        int userid=Integer.parseInt(req.getParameter("userid"));
        String password= req.getParameter("password");
        boolean passwordUpdated=false;
        Properties dbProps = new Properties();
        dbProps.setProperty("url", "jdbc:mysql://db.sporadic.nz/xliu617");
        dbProps.setProperty("user", "xliu617");
        dbProps.setProperty("password", "123");

        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
            try (PreparedStatement stmt = conn.prepareStatement("call UpdatePassword (?,?)")) {
                stmt.setInt(1,userid);
                stmt.setString(2,password);
                stmt.executeUpdate();
                passwordUpdated=true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            passwordUpdated=false;
        }

        out.println();
        if(passwordUpdated){
            out.println("<body>\n" +
                    "    <div class=\"container\">\n" +
                    "\n" +
                    "        <img src=\"WebMaterial/avi_4.png\" width=\"200px\" class=\"image\">\n" +
                    "\n" +
                    "        <p class=\"text_style1\"> Your password has been updated <span class=\"text_highlight\"> SUCCESSFULLY </span> <span id=\"username\" class=\"text_highlight\"> </span> You can now head to the login page</p>\n" +
                    "        \n" +
                    "    </div>\n" +
                    "    \n" +
                    "</body>\n" +
                    "</html>");

        }
        else if(!passwordUpdated){
            out.println("<body>\n" +
                    "    <div class=\"container\">\n" +
                    "\n" +
                    "        <img src=\"WebMaterial/avi_1.png\" width=\"200px\" class=\"image\">\n" +
                    "\n" +
                    "        <p class=\"text_style1\"> Your password update is <span class=\"text_highlight\"> UNSUCCESSFUL </span> <span id=\"username\" class=\"text_highlight\"> </span> Please contact customer service at uranusblogpasswordreset@outlook.com</p>\n" +
                    "\n" +
                    "    </div>\n" +
                    "\n" +
                    "    </body>\n" +
                    "</html>");

        }
    }
}
