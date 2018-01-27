package UranusBlog.Controller.Article;

import UranusBlog.DAO.ArticleDAO;
import UranusBlog.DB.MySQLDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

public class ArticleDeleteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        int userID = Integer.parseInt(req.getParameter("userID"));
//        int articleID = Integer.parseInt(req.getParameter("articleID"));
        int userID = 1;
        int articleID = 1;

        boolean articleExists = false;
        boolean articleAlreadyDeleted = false;
        boolean wasDeleted = false;

        PrintWriter out = resp.getWriter();

        // TODO: VERY INPORTANT!!! NEED validation!!
        // basic logic:
        // if the user is the author or admin, process the deletion, or forbid it

        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            dao.deleteArticle(articleID);
            out.print("{result:\"success\"}");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        Properties dbProps = new Properties();
//        dbProps.setProperty("url", "jdbc:mysql://db.sporadic.nz/xliu617");
//        dbProps.setProperty("user", "xliu617");
//        dbProps.setProperty("password", "123");
//        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
//
//            try (PreparedStatement stmt = conn.prepareStatement("call GetArticleById (?, ?)")) {
//                stmt.setInt(1, userID);
//                stmt.setInt(2, articleID);
//                try (ResultSet r = stmt.executeQuery()) {
//
//                    if (r == null) {
//                        articleExists = false;
//
//                    } else if (r.next()) {
//                        int aID = r.getInt(1);
//
//                        if (aID == articleID) {
//                            articleExists = true;
//                            System.out.println("true");
//                        }
//
////                        else if (aID == articleID) {
////                            articleAlreadyDeleted = true;
////                            System.out.println("already deleted");
////
////                        }
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        if (articleExists) {
//
//            try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
//
//                try (PreparedStatement stmt = conn.prepareStatement("call DeleteArticle (?)")) {
//                    stmt.setInt(1, articleID);
//                    stmt.executeQuery();
//                    wasDeleted = true;
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        }
////
//
//        if (wasDeleted) {
//            out.println("<p> article was deleted! </p>");
//        } else if (!wasDeleted)
//            out.println("<p> article wasn't deleted! </p>");

    }
}