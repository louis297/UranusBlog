package UranusBlog.Controller.Article;

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
import java.util.IntSummaryStatistics;
import java.util.Properties;

public class ArticleAddController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // need to get the username / userID based on the session...

        int userID = 2;
        String title= req.getParameter("title");
        String content = req.getParameter("content");
        String postTime = req.getParameter("postTime");
        int isPrivate = Integer.parseInt(req.getParameter("isPrivate"));

//        String title= "TitleTest";
//        String content = "Content Test";
//        String postTime = "2018-01-24";

        PrintWriter out = resp.getWriter();
        boolean completed=false;

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
            try (PreparedStatement stmt = conn.prepareStatement("call InsertArticle(?,?,?,?,?)")) {
                stmt.setInt(1, userID);
                stmt.setString(2, title);
                stmt.setString(3, content);
                stmt.setString(4, postTime);
                stmt.setInt(5, isPrivate);
                stmt.executeUpdate();
                completed = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (completed) {
            out.println("<p> article posted sucessfully!");
        }
        else {
            out.println("<p> article not posted!!");
        }
    }
}