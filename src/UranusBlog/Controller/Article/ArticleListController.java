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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * possible parameters:
 * all = true / false
 * amount = (int)
 * start = (int)
 */
public class ArticleListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        int userID = Integer.parseInt(req.getParameter("userID"));
//        int start = Integer.parseInt(req.getParameter("start"));
//        int amount = Integer.parseInt(req.getParameter("amount"));
//        boolean usersArticlesOnly = (req.getParameter("own");

        boolean usersArticlesOnly = Boolean.parseBoolean(req.getParameter("own"));

//        int userID = 2;
        Integer userID = (Integer) req.getSession().getAttribute("userID");
        if(userID == null)
            userID = 0;
        Integer start = (Integer) req.getSession().getAttribute("start");
        if(start == null)
            start = 0;
        Integer amount = (Integer) req.getSession().getAttribute("amount");
        if(amount == null)
            amount = 50;
//        int start = 0;
//        int amount = 5;

        PrintWriter out = resp.getWriter();


        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            List<Article> articles;
            if (usersArticlesOnly) {
                articles = dao.getArticles(userID, start, amount, true);
            } else {
                articles = dao.getArticles(userID, start, amount, false);
            }
            JSONArray jsonArray = constructJSON(articles);
            if(jsonArray.isEmpty()){
                out.print("{\"result\":\"fail\", \"reason\":\"No article found\"}");
            } else {
                out.print(jsonArray);
            }
        } catch (Exception e) {
            out.print("{\"result\":\"fail\", \"reason\":\"Server error\"}");
            e.printStackTrace();
        }

    }

    private JSONArray constructJSON(List<Article> articles) throws SQLException {
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

            jsonSingle.put("Hiden_Show", article.getActive());
            jsonArray.add(jsonSingle);
        }
        return jsonArray;
    }
}

