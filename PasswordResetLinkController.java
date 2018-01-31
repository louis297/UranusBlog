package UranusBlog.Controller.Account;

import org.json.simple.JSONObject;
import sun.net.smtp.SmtpClient;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.Properties;

public class PasswordResetLinkController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        PrintWriter out = resp.getWriter();
//        out.println("hello login");
     //   super.doGet(req,resp);
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("Username");

        PrintWriter out = resp.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String userEmail="";
        Properties dbProps = new Properties();
        dbProps.setProperty("url", "jdbc:mysql://db.sporadic.nz/xliu617");
        dbProps.setProperty("user", "xliu617");
        dbProps.setProperty("password", "123");
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM account WHERE username = ?;")) {
                stmt.setString(1, userName);
                try (ResultSet r = stmt.executeQuery()) {
                    while (r.next()) {
                        userEmail = r.getString(r.findColumn("email"));
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //https://www.cs.cmu.edu/~pattis/15-1XX/common/handouts/ascii.html
        //Generate a string (key) for the Get request
        String paramStringForPassReset="";
        for (int i = 0; i <30 ; i++) {
            int randomCharInt= (int)(Math.random()*26)+65;
            char randomChar=(char)randomCharInt;
            paramStringForPassReset+=randomChar;
        }
        System.out.println(paramStringForPassReset);

        //Create new JSONfile to store what string (key) has been made
        ServletContext servletContext = getServletContext();
        //<=Create new Folder in Web Folder Called PasswordReset, put a place holder in it=>
        String path = servletContext.getRealPath("/PasswordReset");
        File jsonFile = new File(path+paramStringForPassReset+".json");



        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO passwordreset VALUES (?, ?);")) {
                stmt.setString(1, paramStringForPassReset);
                stmt.setString(2, userName);
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        boolean success= sendEmailToUser(userEmail,userName);
        if (success){
            out.println("<p>Password link successfully sent to user </p>");
        }
        else if(!success){
            out.println("<p> Password reset unsuccessful </p>");
        }



    }

    public boolean sendEmailToUser(String userEmail, String userName)  {
        boolean emailSuccess=true;
        try {
            SmtpClient smtpClient = new SmtpClient();
            //email adress of admin is uranusblogpasswordreset@outlook.com, the password is louisjamesdan123
            smtpClient.from("uranusblogpasswordreset@outlook.com");
            smtpClient.to(userEmail);
            PrintStream msg = smtpClient.startMessage();

            msg.println("Dear" + userName + ":");
            msg.println("We have received your request for a password reset, which you can do by clicking on the link below. ");
            //Ask Andrew
            msg.println();
            msg.println("Yours Faithfully- UranusBlog ManagementTeam");
            smtpClient.closeServer();
        }
        catch (IOException e) {
            System.out.println("There was a problem handling the submission...");
            emailSuccess=false;
        }

        return emailSuccess;
    }

}