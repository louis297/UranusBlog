package UranusBlog.Controller.Article;

import UranusBlog.DAO.ArticleDAO;
import UranusBlog.DB.MySQLDatabase;
import UranusBlog.Model.Comment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class CommentListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer articleID = (Integer) session.getAttribute("aid");
        Integer userID = (Integer) session.getAttribute("userID");
        if(userID == null){
            userID = 0;
        }
        PrintWriter out = resp.getWriter();
        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            List<Comment> comments= dao.getComments(articleID, userID);
            JSONArray jsonArray = constructJSON(comments);
            out.print(jsonArray);
        } catch (Exception e) {
            out.print("{\"result\":\"fail\", \"reason\":\"Server error\"}");
            e.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private JSONArray constructJSON(List<Comment> comments){
        JSONArray jsonArray = new JSONArray();
        for (Comment comment : comments) {
            JSONObject jsonSingle = new JSONObject();
            jsonSingle.put("content", comment.getContent());
            jsonSingle.put("createdTime", comment.getCreatedTime());
            jsonSingle.put("author", comment.getUsername());
            jsonSingle.put("avatarPath", comment.getAvatarPath());
        }
        return  jsonArray;
    }
}
