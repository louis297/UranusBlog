package UranusBlog.Controller.Article;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

/**
 * possible parameters:
 * all = true / false
 * amount = (int)
 * start = (int)
 */
public class ArticleListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int userID = 2;
//        int usersArticlesOnly = Integer.parseInt(req.getParameter("own"));
//        int start = Integer.parseInt(req.getParameter("start"));
//        int amount = Integer.parseInt(req.getParameter("amount"));
        int usersArticlesOnly = 1;
        int start = 0;
        int amount = 2;

        PrintWriter out = resp.getWriter();

        boolean articlesReturned = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        int aid = Integer.parseInt(req.getParameter("aid"));

        Properties dbProps = new Properties();
        dbProps.setProperty("url", "jdbc:mysql://db.sporadic.nz/xliu617");
        dbProps.setProperty("user", "xliu617");
        dbProps.setProperty("password", "123");
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {

            if (usersArticlesOnly == 1) {
                try (PreparedStatement stmt = conn.prepareStatement("call GetArticleListOwn (?,?,?)")) {
                    stmt.setInt(1, userID);
                    stmt.setInt(2, start);
                    stmt.setInt(3, amount);
                    try (ResultSet r = stmt.executeQuery()) {

                        if (r != null) {
                            articlesReturned = false;
                        }
                        while (r.next()) {
                            String title = r.getString(3);
                            String content = r.getString(4);
                            out.println("<h1>" + title + "</h1><br>");
                            out.println("<p>" + content + "</p>");
                            articlesReturned = true;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (usersArticlesOnly == 0) {
                try (PreparedStatement stmt = conn.prepareStatement("call GetArticleListAll(?,?)")) {
                    stmt.setInt(1, start);
                    stmt.setInt(2, amount);
                    try (ResultSet r = stmt.executeQuery()) {

                        if (r != null) {
                            articlesReturned = false;
                        }
                        while (r.next()) {
                            String title = r.getString(3);
                            String content = r.getString(4);
                            out.println("<h1>" + title + "</h1><br>");
                            out.println("<p>" + content + "</p>");
                            articlesReturned = true;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!articlesReturned) {
            out.println("<p> that didn't work </p>");
        }
    }
}
