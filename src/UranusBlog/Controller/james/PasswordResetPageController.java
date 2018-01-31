package UranusBlog.Controller.james;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.Properties;

public class PasswordResetPageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        String key = req.getParameter("key");

        String username = "";
        boolean validRequest = false;


        Properties dbProps = new Properties();
        dbProps.setProperty("url", "jdbc:mysql://db.sporadic.nz/xliu617");
        dbProps.setProperty("user", "xliu617");
        dbProps.setProperty("password", "123");

        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT* FROM passwordreset WHERE `key`=?")) {
                stmt.setString(1, key);
                try (ResultSet r = stmt.executeQuery()) {
                    while (r.next()) {
                        if (r != null) {
                            validRequest = true;
                            username = r.getString(2);
                        } else {
                            validRequest = false;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

        if (!validRequest) {
            out.println("<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"row\">\n" +
                    "            <div class=\" col-sm-12 col-md-8 offset-md-2\">\n" +
                    "                <img src=\"WebMaterial/avi_2.png\" class=\"image\" width=\"200px\">\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <div class=\"row\">\n" +
                    "            <p class=\"text_wrong\"> <span class=\"text_highlight\">Oops!!!</span> Something seem wrong! You have submitted an invalid link!</p>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <div class=\"row\">\n" +
                    "            <p class=\"text_wrong\"> Please re-enter the link, alternative contact <b class=\"text_style1\"> uranusblogpasswordreset@outlook.com </b> for futher assistance</p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>");
        } else if (validRequest) {
            out.println("<body>\n" +
                    "\n" +
                    "    <div class=\"container\">\n" +
                    "\n" +
                    "        <img src=\"WebMaterial/avi_3.png\" width=\"200px\" class=\"image\">\n" +
                    "\n" +
                    "        <p class=\"text_style1\"> <span class=\"text_highlight\"> Hello </span> <span id=\"username\" class=\"text_highlight\"> </span> ! Welcome Back to Uranus Blog.</p>\n" +
                    "\n" +
                    "        <p > You can reset your password using the form below! </p>\n" +
                    "\n" +
                    "        <form action=\"/\" method=\"post\">\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label for=\"pass1\">Password</label>\n" +
                    "                <input type=\"password\" class=\"form-control\" id=\"pass1\" placeholder=\"Enter Your New Password\" name=\"password\">\n" +
                    "            </div>\n" +
                    "            <div class=\"form-group\">\n" +
                    "                <label for=\"pass2\">Re-enter Password</label>\n" +
                    "                <input type=\"password\" class=\"form-control\" id=\"pass2\" placeholder=\"Re-enter Your Password\" name=\"passwordretype\">\n" +
                    "            </div>\n" +
                    "            <button type=\"submit\" class=\"btn btn-muted\">Save Change</button>\n" +
                    "        </form>\n" +
                    "        <div>\n" +
                    "            <p id=\"password_reminder\">    </p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}

