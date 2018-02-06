package UranusBlog.Controller.Account;

import sun.net.smtp.SmtpClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

public class PasswordUpdateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        HttpSession passwordSess=req.getSession(true);
        String key=passwordSess.getAttribute("keypass").toString();
        String username="";
        int userid=0;
        System.out.println(key+"dan left test");

        Properties dbProps = new Properties();
        dbProps.setProperty("url", "jdbc:mysql://db.sporadic.nz/xliu617");
        dbProps.setProperty("user", "xliu617");
        dbProps.setProperty("password", "123");

        try (Connection conn1 = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
            try (PreparedStatement stmt = conn1.prepareStatement("SELECT* FROM passwordreset WHERE keypass=?")) {
                stmt.setString(1, key);
                try (ResultSet r = stmt.executeQuery()) {
                    while (r.next()) {
                        username = r.getString(2);
                    }
                }
            }
            try (PreparedStatement stmt = conn1.prepareStatement("SELECT* FROM account WHERE username=?")) {
                stmt.setString(1, username);
                try (ResultSet r = stmt.executeQuery()) {
                    while (r.next()) {
                        userid = r.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        String password= req.getParameter("password");
        boolean passwordUpdated=false;

        if(userid!=0){
            try (Connection conn3 = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
                try (PreparedStatement stmt = conn3.prepareStatement("call UpdatePassword (?,?)")) {
                    stmt.setInt(1,userid);
                    stmt.setString(2,password);
                    stmt.executeUpdate();
                    passwordUpdated=true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                passwordUpdated=false;
            }
        }



        out.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Main</title>\n" +
                "\n" +
                "    <link rel=\"stylesheet\" href=\"static/css/bootstrap.css\">\n" +
                "    <script src=\"static/js/jquery.min.js\"></script>\n" +
                "    <script src=\"static/js/popper.min.js\"></script>\n" +
                "    <script src=\"static/js/bootstrap.js\"></script>\n" +
                "\n" +
                "    <style>\n" +
                "        .image{\n" +
                "            display: block;\n" +
                "            margin-left: auto;\n" +
                "            margin-right: auto;\n" +
                "        }\n" +
                "\n" +
                "        p{\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .text_highlight{\n" +
                "            font-weight: bold;\n" +
                "            font-size: 150%;\n" +
                "            color:darkred;\n" +
                "        }\n" +
                "\n" +
                "        .text_style1{\n" +
                "            font-weight: bold;\n" +
                "            font-size: 120%;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "\n" +
                "    <script>\n" +
                "        $(document).ready(function() {\n" +
                "            $(\"#pass2\").focusout(function () {\n" +
                "                var value1 = $(\"#pass1\").val();\n" +
                "                var value2 = $(\"#pass2\").val();\n" +
                "                if (value1 == value2) {\n" +
                "                    $(\"#password_reminder\").css({\"color\": \"green\", \"font-weight\": \"bold\"});\n" +
                "                    document.getElementById(\"password_reminder\").innerHTML = \"Password matches!\";\n" +
                "                    console.log(\"1\");\n" +
                "                }\n" +
                "                else if (value1 != value2)  {\n" +
                "                    $(\"#password_reminder\").css({\"color\": \"red\", \"font-weight\": \"bold\"});\n" +
                "                    document.getElementById(\"password_reminder\").innerHTML = \"Password does not match! Please re-enter your password.\";\n" +
                "                    console.log(\"2\");\n" +
                "                }\n" +
                "            })\n" +
                "        });\n" +
                "\n" +
                "    </script>\n" +
                "</head>");

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
            passwordSess.invalidate();
            

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
