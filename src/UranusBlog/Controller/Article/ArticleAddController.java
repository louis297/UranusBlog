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
import java.util.IntSummaryStatistics;
import java.util.Properties;

public class ArticleAddController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: user super.doGet to replace doPost in product environment
        //super.doGet(req, resp);
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        String postTimeStr = req.getParameter("postTime");
        String isPrivateStr = req.getParameter("isPrivate");
        // TODO: get userID from session
        int userID = 2;
        String title= req.getParameter("title");
        String content = req.getParameter("content");

        if(title == null || title.isEmpty() ||
                content == null || content.isEmpty() ||
                postTimeStr == null || postTimeStr.isEmpty() ||
                isPrivateStr == null || isPrivateStr.isEmpty()){
            out.print("{result:\"fail\"}");
            return;
        }

        Timestamp postTime = Timestamp.valueOf(postTimeStr);
        boolean isPrivate = Boolean.parseBoolean(isPrivateStr);

//        String title= "TitleTest";
//        String content = "Content Test";
//        String postTime = "2018-01-24";




        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            dao.addArticle(userID, title, content, postTime, isPrivate);
            out.print("{result:\"success\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("{result:\"fail\"}");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{result:\"fail\"}");
        }
    }
}