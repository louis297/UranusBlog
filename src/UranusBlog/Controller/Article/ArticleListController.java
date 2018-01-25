package UranusBlog.Controller.Article;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        PrintWriter out = resp.getWriter();

        boolean own= Boolean.parseBoolean(req.getParameter("own"));
        int start= Integer.parseInt(req.getParameter("start"));
        int amount= Integer.parseInt(req.getParameter("amount"));
       // HttpSession thisSession= req.getSession();
        //String userID= thisSession.getAttribute("userid").toString();

        own= true;
        start=1;
        amount=3;
        String userID="1";

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
            if (own==true){
            try (PreparedStatement stmt = conn.prepareStatement("call GetArticleListAll (?,?)")) {
                stmt.setInt(1, start);
                stmt.setInt(2, amount);
                try (ResultSet r = stmt.executeQuery()) {
                    while (r.next()) {
                        String title = r.getString(3);
                        String author = r.getString(2);
                        String content = r.getString(4);
                        int privacy = Integer.parseInt(r.getString(9));
                            if (author.equals(userID)) {
                                out.println("<p>" + title + content + " </P>");
                            }
                        }
                    }
                }
            }

            else if (own==false){
                    try (PreparedStatement stmt = conn.prepareStatement("call GetArticleListAll (?,?)")) {
                        stmt.setInt(1, start);
                        stmt.setInt(2, amount);
                        try (ResultSet r = stmt.executeQuery()) {
                            while (r.next()) {
                                String title = r.getString(3);
                                String author = r.getString(2);
                                String content = r.getString(4);
                                int privacy = Integer.parseInt(r.getString(9));
                                if (privacy == 1) {
                                    if (author.equals(userID)) {
                                        out.println("<p>" + title + content + " </P>");
                                    }
                                } else {
                                    out.println("<p>" + title + content + " </P>");
                                }
                            }
                        }
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
