package UranusBlog.Controller.Article;

import UranusBlog.DAO.ArticleDAO;
import UranusBlog.DB.MySQLDatabase;
import UranusBlog.Utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.IntSummaryStatistics;
import java.util.Properties;

public class ArticleAddController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
//        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();

        String postTimeStr = req.getParameter("postTime");
        String isPrivateStr = req.getParameter("isPrivate");
        // uid from session
        Integer userID = (Integer) session.getAttribute("userID");
        String title= req.getParameter("title");
        String content = req.getParameter("content");

        if(title == null || title.isEmpty() ||
                content == null || content.isEmpty() ||
                postTimeStr == null || postTimeStr.isEmpty() ||
                isPrivateStr == null || isPrivateStr.isEmpty()){
            out.print("{\"result\":\"fail\",\"reason\":\"The required fields are missing\"}");
            return;
        }
        System.out.println(postTimeStr);
        Timestamp postTime;
        try {
            postTime = Timestamp.valueOf(postTimeStr);
        } catch (IllegalArgumentException e){
            out.print("{\"result\":\"fail\",\"reason\":\"Post time format error.\"}");
            return;
        }
        boolean isPrivate = Boolean.parseBoolean(isPrivateStr);

//        String title= "TitleTest";
//        String content = "Content Test";
//        String postTime = "2018-01-24";

        // uid of 0 is guest
        if(userID == null || userID == 0){
            out.print("{\"result\":\"fail\",\"reason\":\"Please login or register first\"}");
            return;
        }

        if(title.length() > 100){
            out.print("{\"result\":\"fail\",\"reason\":\"The title is too long\"}");
            return;
        }

        content = Utils.contentPrepare(content);

        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            int aid = dao.addArticle(userID, title, content, postTime, isPrivate);
            out.print("{\"result\":\"success\",\"aid\":\"" + aid + "\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("{\"result\":\"fail\",\"reason\":\"Database error\"}");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"result\":\"fail\",\"reason\":\"Unknown server error\"}");
        }
    }
}