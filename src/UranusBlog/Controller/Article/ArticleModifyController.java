package UranusBlog.Controller.Article;

import UranusBlog.DAO.ArticleDAO;
import UranusBlog.DB.MySQLDatabase;
import UranusBlog.Model.Article;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

public class ArticleModifyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession();

        // uid from session
        Integer userID = (Integer) session.getAttribute("userID");
        String userRole = (String) session.getAttribute("roleDetail");

        // uid of 0 is guest
        if(userID == null || userID == 0 || userRole == null){
            out.print("{\"result\":\"fail\",\"reason\":\"Please register first\"}");
            return;
        }

        String articleIDStr = req.getParameter("aid");

        System.out.println("after int delcaration");

//        String title= "hello louis this is the test for article modifier";
//        String content="Hello!";
//        String postTimeStr="2018-01-24 20:29:42";
//        String isPrivateStr="false";

        String title=req.getParameter("title");
        String content=req.getParameter("content");
        String postTimeStr=req.getParameter("postTime");
        String isPrivateStr=req.getParameter("isPrivate");

        // 1. validate the input data
        if(title == null || title.isEmpty() ||
                content == null || content.isEmpty() ||
                postTimeStr == null || postTimeStr.isEmpty() ||
                isPrivateStr == null || isPrivateStr.isEmpty()){
            out.print("{\"result\":\"fail\", \"reason\":\"Input data is invalid\"}");
            return;
        }

        Integer articleID = Integer.parseInt(articleIDStr);

        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            // 2. check if the user can modify the article
            // 3. check if article exist (since front-end hacking may send invalid articleID information
            Article article = dao.getArticleById(userID, articleID);
            if(article == null){
                out.print("{\"result\":\"fail\", \"reason\":\"Cannot access to article\"}");
                return;
            } else if(!article.getAuthorId().equals(userID) || userRole.equals("admin")){
                out.print("{\"result\":\"fail\", \"reason\":\"No authorization to modify the article.\"}");
                return;
            }

            Timestamp postTime = Timestamp.valueOf(postTimeStr);
            boolean modifiedPrivacy= Boolean.parseBoolean(isPrivateStr);

            // 4. do modify
            dao.updateArticle(articleID, title, content, postTime, modifiedPrivacy);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

 }
