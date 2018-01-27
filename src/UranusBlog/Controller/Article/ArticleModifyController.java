package UranusBlog.Controller.Article;

import UranusBlog.DAO.ArticleDAO;
import UranusBlog.DB.MySQLDatabase;
import UranusBlog.Model.Article;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        //String articleid= req.getParameter("articleid");
        //String userId=req.getParameter("userId");
        Integer userID=2;
        Integer articleID=2;

        System.out.println("after int delcaration");

        String title= "hello louis this is the test for article modifier";
        String content="Hello!";
        String postTimeStr="2018-01-24 20:29:42";
        String isPrivateStr="false";


        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            // 1. check if the user can modify the article
                // TODO: as above

            // 2. check if article exist (since front-end hacking may send invalid articleID information
            Article article = dao.getArticleById(userID, articleID);
            if(article == null){
                out.print("{result:\"fail\", reason:\"Article does not exist.\"}");
                return;
            }

            // 3. validate the input data
            if(title == null || title.isEmpty() ||
                    content == null || content.isEmpty() ||
                    postTimeStr == null || postTimeStr.isEmpty() ||
                    isPrivateStr == null || isPrivateStr.isEmpty()){
                out.print("{result:\"fail\", reason:\"Input data is invalid.\"}");
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
