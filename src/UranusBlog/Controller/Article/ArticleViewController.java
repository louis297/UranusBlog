package UranusBlog.Controller.Article;

import UranusBlog.DAO.ArticleDAO;
import UranusBlog.DB.MySQLDatabase;
import UranusBlog.Model.Article;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
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

        int articleID = Integer.parseInt(req.getParameter("aid"));

        // TODO: userID should ge got from session, if it doesn't exist, set it to 0 (we add a special user for guest)
        int userID = 2;
//        int aID = 20;

        PrintWriter out = resp.getWriter();

        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            Article article = dao.getArticleById(userID, articleID);
            if (article != null) {
                JSONObject jsonObject = new JSONObject();
                SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
                jsonObject.put("article_id", article.getArticleId());
                jsonObject.put("title", article.getTitle());
                jsonObject.put("content", article.getContent());
                jsonObject.put("created_time", sdf.format(article.getCreatedTime()));
                jsonObject.put("modified_time", sdf.format(article.getModifiedTime()));
                jsonObject.put("post_time", sdf.format(article.getPostTime()));
                jsonObject.put("isPrivate", article.getPrivate());
                jsonObject.put("authorName", article.getAuthorName());
                out.print(jsonObject);
            } else {
                out.print("{\"result\":\"fail\"}");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
