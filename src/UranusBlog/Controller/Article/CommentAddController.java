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
import java.sql.SQLException;

public class CommentAddController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();

        // uid from session
        Integer userID = (Integer) session.getAttribute("userID");
        String content = req.getParameter("content");
        String articleIDStr = req.getParameter("aid");

        if(content == null || content.isEmpty()
                || articleIDStr == null || articleIDStr.isEmpty()) {
            out.print("{\"result\":\"fail\",\"reason\":\"The required fields are missing\"}");
            return;
        }
        Integer articleID = Integer.parseInt(articleIDStr);

        // uid of 0 is guest
        if(userID == null || userID == 0){
            out.print("{\"result\":\"fail\",\"reason\":\"Please register first\"}");
            return;
        }

        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            Article article = dao.getArticleById(userID, articleID);
            if(article == null) {
                out.print("{\"result\":\"fail\",\"reason\":\"You cannot access to this article.\"}");
            } else {
                dao.addComment(userID, articleID, content);
                out.print("{\"result\":\"success\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("{\"result\":\"fail\",\"reason\":\"Database error\"}");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"result\":\"fail\",\"reason\":\"Unknown server error\"}");
        }
    }
}
