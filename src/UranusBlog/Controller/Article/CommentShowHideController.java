package UranusBlog.Controller.Article;

import UranusBlog.DAO.AccountDAO;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class CommentShowHideController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        int commentID = Integer.parseInt(req.getParameter("commentid"));
        Boolean active= Boolean.parseBoolean(req.getParameter("active"));
//        HttpSession Sess=req.getSession(true);
//        int userid=Integer.parseInt(Sess.getAttribute("userID").toString());
        Comment comment;

        if (req.getParameter("active")==null){
            try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
                comment=dao.getCommentById(commentID);
                active=comment.isActive();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (req.getParameter("active")!=null) {
            active = Boolean.parseBoolean(req.getParameter("active"));
        }


        if (!active) {
            try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
                dao.resumeComment(commentID);
                out.println("Comment : " + commentID+ " is no longer hidden");
            } catch (Exception e) {
                out.print("{\"result\":\"fail\", \"reason\":\"Server error\"}");
                e.printStackTrace();
            }
        } else if (active) {
            try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
                dao.deleteComment(commentID);
                out.println("Comment: " + commentID + "is now hidden");
            } catch (Exception e) {
                out.print("{\"result\":\"fail\", \"reason\":\"Server error\"}");
                e.printStackTrace();
            }

        }
    }
}
