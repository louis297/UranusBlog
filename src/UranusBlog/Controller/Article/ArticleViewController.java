package UranusBlog.Controller.Article;

import UranusBlog.DAO.ArticleDAO;
import UranusBlog.DB.MySQLDatabase;
import UranusBlog.Model.Article;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        PrintWriter out = resp.getWriter();

//        int articleID = Integer.parseInt(req.getParameter("aid"));
        String articleIDStr = req.getParameter("aid");
        if(articleIDStr == null || articleIDStr.isEmpty() || !articleIDStr.matches("^[+-]?\\d+$")){
            out.print("{\"result\":\"fail\", \"reason\":\"No valid article ID provided\"}");
            return;
        }
        int articleID = Integer.parseInt(articleIDStr);

        // TODO: userID should ge got from session, if it doesn't exist, set it to 0 (we add a special user for guest)
//        int userID = 2;
        HttpSession session = req.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        if(userID == null)
            userID = 0;
        Integer authorRole = (Integer) session.getAttribute("roleID");
        if(authorRole == null)
            authorRole = 3;

        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            Article article = dao.getArticleById(userID, articleID);
            if (article != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("result","success");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
                jsonObject.put("article_id", article.getArticleId());
                jsonObject.put("title", article.getTitle());
                jsonObject.put("content", article.getContent());
                jsonObject.put("created_time", sdf.format(article.getCreatedTime()));
                jsonObject.put("modified_time", sdf.format(article.getModifiedTime()));
                jsonObject.put("post_time", sdf.format(article.getPostTime()));
                jsonObject.put("isPrivate", article.getPrivate());
                jsonObject.put("authorName", article.getAuthorName());
                jsonObject.put("authorID", article.getAuthorId());
                jsonObject.put("authorRole", authorRole);
                Boolean isOwn;
                if (session.getAttribute("is_logged") == null || !((Boolean) session.getAttribute("is_logged"))){
                    isOwn = false;
                } else {
                    isOwn = session.getAttribute("userID").equals(article.getAuthorId()) ||
                            session.getAttribute("roleDetail").equals("admin");
                }
                // "admin" is considered as the owner of all the articles
                jsonObject.put("isOwnArticle", isOwn);
                out.print(jsonObject);
            } else {
                out.print("{\"result\":\"fail\",\"reason\":\"Incorrect article ID or access denied.\"}");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
