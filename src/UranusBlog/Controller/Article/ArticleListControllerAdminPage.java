package UranusBlog.Controller.Article;

import UranusBlog.DAO.ArticleDAO;
import UranusBlog.DB.MySQLDatabase;
import UranusBlog.Model.Article;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * possible parameters:
 * all = true / false
 * amount = (int)
 * start = (int)
 */
public class ArticleListControllerAdminPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        Properties dbProps = new Properties();
        dbProps.setProperty("url", "jdbc:mysql://db.sporadic.nz/xliu617");
        dbProps.setProperty("user", "xliu617");
        dbProps.setProperty("password", "123");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Article> articles = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM article;")) {
                try (ResultSet r = stmt.executeQuery()) {
                    while (r.next()) {
                        int articleid = r.getInt(1);
                        int authorid = r.getInt(2);
                        String title = r.getString(3);
                        String content = r.getString(4);
                        Timestamp cd = r.getTimestamp(5);
                        Timestamp md = r.getTimestamp(6);
                        Timestamp pd = r.getTimestamp(7);
                        int active = r.getInt(r.findColumn("active"));
                        boolean acitveBoolean = false;
                        if (active == 0) {
                            acitveBoolean = false;
                        } else if (active == 1) {
                            acitveBoolean = true;
                        }
                        int privat = r.getInt(r.findColumn("private"));
                        boolean privateBoolean = false;
                        if (active == 0) {
                            privateBoolean = false;
                        } else if (active == 1) {
                            privateBoolean = true;
                        }

                        String authorname = "";

                        try (PreparedStatement stmt2 = conn.prepareStatement("SELECT* FROM account WHERE uid=?")) {
                            stmt2.setInt(1, authorid);
                            System.out.println("stmt2 created");
                            try (ResultSet rs = stmt2.executeQuery()) {
                                while (rs.next()) {
                                    authorname = rs.getString(2);
                                }
                            }
                            Article single = new Article(articleid, authorid, title, content, cd, md, pd, acitveBoolean, privateBoolean, authorname);
                            System.out.println(single);
                            articles.add(single);
                        }

                    }
                }
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                JSONArray jsonArray = constructJSON(articles);
                if (jsonArray.isEmpty()) {
                    out.print("{\"result\":\"fail\", \"reason\":\"No article found\"}");
                    System.out.println("json not returned");
                } else {
                    out.print(jsonArray);
                    System.out.println("json returned");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        private JSONArray constructJSON (List < Article > articles) throws SQLException {
            JSONArray jsonArray = new JSONArray();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            for (Article article : articles) {

                JSONObject jsonSingle = new JSONObject();

                jsonSingle.put("article_id", article.getArticleId());
                jsonSingle.put("title", article.getTitle());
                jsonSingle.put("content", article.getContent());
                jsonSingle.put("created_time", sdf.format(article.getCreatedTime()));
                jsonSingle.put("modified_time", sdf.format(article.getModifiedTime()));
                jsonSingle.put("post_time", sdf.format(article.getPostTime()));
                jsonSingle.put("is_private", article.getPrivate());
                jsonSingle.put("author_name", article.getAuthorName());
                //modified here
                jsonSingle.put("Hiden_Show", article.getActive());

                jsonArray.add(jsonSingle);
            }
            return jsonArray;
        }
    }

