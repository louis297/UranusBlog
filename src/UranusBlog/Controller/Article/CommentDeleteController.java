package UranusBlog.Controller.Article;

import UranusBlog.DAO.ArticleDAO;
import UranusBlog.DB.MySQLDatabase;
import UranusBlog.Model.Article;
import UranusBlog.Model.Comment;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class CommentDeleteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int commentID = Integer.parseInt(req.getParameter("cid"));

        PrintWriter out = resp.getWriter();

        // basic logic of validation:
        // if the user is the author or admin, process the deletion, or forbid it
        HttpSession session = req.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        if(userID == null )
            userID = 0;
        String userRole = (String) session.getAttribute("roleDetail");
        if(userRole == null)
            userRole = "guest";

        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            Comment comment = dao.getCommentById(commentID);
            if(comment == null){
                out.print("{\"result\":\"fail\", \"reason\":\"No comment found\"}");
            } else if(userID.equals(comment.getAuthorID()) && !userRole.equals("admin")) {
                dao.deleteComment(commentID);
                out.print("{\"result\":\"success\"}");
            } else {
                out.print("{\"result\":\"fail\", \"reason\":\"No authorization to delete this comment\"}");
            }
        } catch (Exception e) {
            out.print("{\"result\":\"fail\", \"reason\":\"Server error\"}");
            e.printStackTrace();
        }
    }
}