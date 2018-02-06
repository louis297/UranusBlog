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
//        super.doGet(req,resp);
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userEmail="";
        Properties dbProps = new Properties();
        dbProps.setProperty("url", "jdbc:mysql://db.sporadic.nz/xliu617");
        dbProps.setProperty("user", "xliu617");
        dbProps.setProperty("password", "123");

        String userName= req.getParameter("username");
        int userID=Integer.parseInt(req.getParameter("userid"));

        if (req.getParameter("username")==null || req.getParameter("userid")!=null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
                try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM account WHERE uid= ?;")) {
                    stmt.setInt(1, userID);
                    try (ResultSet r = stmt.executeQuery()) {
                        while (r.next()) {
                            userName = r.getString(r.findColumn("username"));
                            System.out.println(userName);
                        }

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

//       "jamesku2281993";
//       System.out.println("user name");
//       System.out.println(userName);
         PrintWriter out = resp.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM account WHERE username = ?;")) {
                stmt.setString(1, userName);
                try (ResultSet r = stmt.executeQuery()) {
                    if (!r.next()){
                        out.print("<p> The user does not exist! </p>");
                    }
                    while (r.next()) {
                        userEmail = r.getString(r.findColumn("email"));
                        System.out.println(userEmail);
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
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        boolean success= sendEmailToUser(userEmail ,userName, paramStringForPassReset);
        if (success){
            out.println("<p>Password link successfully sent to user </p> <button  onclick=\"window.history.back()\"> Go Back to Previous Page </button>");
        }
        else if(!success){
            out.println("<p> Password reset unsuccessful </p> <button  onclick=\"window.history.back()\"> Go Back to Previous Page </button>");
        }



    }

    public boolean sendEmailToUser(String userEmail, String userName, String paramStringForPassReset)  {
        boolean emailSuccess=true;
        try {
            SmtpClient smtpClient = new SmtpClient();

            //email adess of admin is uranusblogpasswordreset@outlook.com, the password is louisjamesdan123
            smtpClient.from("uranusblogpasswordreset@outlook.com");
            smtpClient.to(userEmail);

            PrintStream msg = smtpClient.startMessage();

            msg.println("To: " + userEmail);  // so mailers will display the To: address
            msg.println("Subject: Password Reset");
            msg.println();
            //System.out.println("test1");

            msg.println("Dear  " + userName + " :");
            msg.println("");
            msg.println("We have received your request for a password reset, which you can do by clicking on the link below. ");
            //Ask Andrew
            msg.println("");
            msg.println("sporadic.nz/uranusblog/pwresetpage?key="+paramStringForPassReset);
            msg.println("");
            msg.println("Urnaus Blog Management");
            msg.flush();
            smtpClient.closeServer();
        }
        catch (IOException e) {
            System.out.println("There was a problem handling the submission...");
            emailSuccess=false;
        }

        return emailSuccess;

    }

}