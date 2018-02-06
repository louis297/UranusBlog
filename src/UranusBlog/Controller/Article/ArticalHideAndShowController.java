package UranusBlog.Controller.Article;

import UranusBlog.DAO.AccountDAO;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class ArticalHideAndShowController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        int articleID = Integer.parseInt(req.getParameter("articleid"));
        Boolean active= Boolean.parseBoolean(req.getParameter("active"));
        //HttpSession Sess=req.getSession(true);
        //int userid=Integer.parseInt(Sess.getAttribute("userID").toString());
        int userid=1;
        Article article;

        if (req.getParameter("active")==null){
            try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
                article=dao.getArticleById(userid,articleID);
                active=article.getActive();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (req.getParameter("active")!=null) {
            active = Boolean.parseBoolean(req.getParameter("active"));
        }


        if (!active) {
            try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
                dao.resumeArticle(articleID);
                out.println("<p>user: " + articleID + " is no longer hidden </p> <button  onclick=\"window.history.back()\"> Go Back to Previous Page </button>");
            } catch (Exception e) {
                out.print("{\"result\":\"fail\", \"reason\":\"Server error\"}");
                e.printStackTrace();
            }
        } else if (active) {
            try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
                dao.deleteArticle(articleID);
                out.println("<p> user: " + articleID + " is now hidden </p> <button  onclick=\"window.history.back()\"> Go Back to Previous Page </button>");
            } catch (Exception e) {
                out.print("{\"result\":\"fail\", \"reason\":\"Server error\"}");
                e.printStackTrace();
            }

        }
    }
}
