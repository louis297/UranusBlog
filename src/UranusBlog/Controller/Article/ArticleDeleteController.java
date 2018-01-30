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

public class ArticleDeleteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        int userID = Integer.parseInt(req.getParameter("userID"));
        int articleID = Integer.parseInt(req.getParameter("aid"));
//        int articleID = 1;


        PrintWriter out = resp.getWriter();

        // basic logic of validation:
        // if the user is the author or admin, process the deletion, or forbid it
        HttpSession session = req.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        if(userID != null )
            userID = 0;
        String userRole = (String) session.getAttribute("roleDetail");
        if(userRole == null)
            userRole = "guest";

        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            Article article = dao.getArticleById(userID, articleID);
            if(article == null){
                out.print("{\"result\":\"fail\", \"reason\":\"Cannot access to the article\"}");
            } else if(userID.equals(article.getAuthorId()) || userRole.equals("admin")) {
                dao.deleteArticle(articleID);
                out.print("{\"result\":\"success\"}");
            } else {
                out.print("{\"result\":\"fail\", \"reason\":\"No authorization to delete\"}");
            }
        } catch (Exception e) {
            out.print("{\"result\":\"fail\", \"reason\":\"Server error\"}");
            e.printStackTrace();
        }
    }
}