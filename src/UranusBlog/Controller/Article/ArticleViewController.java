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
 * show single article
 */
public class ArticleViewController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        int usersArticlesOnly = Integer.parseInt(req.getParameter("own"));
//        int start = Integer.parseInt(req.getParameter("start"));
//        int amount = Integer.parseInt(req.getParameter("amount"));
        int aID = Integer.parseInt(req.getParameter("aid"));
        int userID = 2;
//        int aID = 20;

        PrintWriter out = resp.getWriter();

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


            try (PreparedStatement stmt = conn.prepareStatement("call GetArticleById (?,?)")) {
                stmt.setInt(1, userID);
                stmt.setInt(2, aID);
                try (ResultSet r = stmt.executeQuery()) {

                    if (r != null && r.next()) {
                        out.print("\'[");
//                            String title = r.getString(3);
//                            String content = r.getString(4);
//                            out.println("<h1>" + title + "</h1><br>");
//                            out.println("<p>" + content + "</p>");

                        String title = r.getString(3);
                        String content = r.getString(4);
                        String created_time = r.getString(5);
                        String modified_time = r.getString(6);
                        String post_time = r.getString(7);
                        String isPrivate = r.getString(9);
                        String authorName = r.getString(10);

                        StringBuffer jsonFormat = new StringBuffer();
                        jsonFormat.append("{");
                        jsonFormat.append("\"title\":\"" + title + "\",");
                        jsonFormat.append("\"content\":\"" + content + "\",");
                        jsonFormat.append("\"created_time\":\"" + created_time + "\",");
                        jsonFormat.append("\"modified_time\":\"" + modified_time + "\",");
                        jsonFormat.append("\"post_time\":\"" + post_time + "\",");
                        jsonFormat.append("\"private\":\"" + isPrivate + "\",");
                        jsonFormat.append("\"authorName\":\"" + authorName + "\",");
                        jsonFormat.append("\"result\":\"" + "\"success\"" + "\"");


                        jsonFormat.append("},");
                        out.println(jsonFormat.toString());
                        out.print("]\'");
                    } else {
                        out.print("'{\"result\":\"fail\"}'");
                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
