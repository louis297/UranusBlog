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
import java.text.SimpleDateFormat;
import java.util.List;

public class CommentListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String articleIDStr = req.getParameter("aid");
        Integer userID = (Integer) session.getAttribute("userID");
        PrintWriter out = resp.getWriter();
        if(userID == null){
            userID = 0;
        }
        if(articleIDStr == null || articleIDStr.isEmpty() || !articleIDStr.matches("^[+-]?\\d+$")){
            out.print("{\"result\":\"fail\", \"reason\":\"No valid article ID provided\"}");
            return;
        }
        Integer articleID = Integer.parseInt(articleIDStr);

        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            List<Comment> comments= dao.getComments(articleID, userID);
            if(comments.size() == 0){
                out.print("{\"result\":\"fail\", \"reason\":\"No comments for this article\"}");
                return;
            }
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
            jsonSingle.put("cid", comment.getCommentID());
            jsonSingle.put("content", comment.getContent());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            jsonSingle.put("createdTime", sdf.format(comment.getCreatedTime()));
            jsonSingle.put("author", comment.getUsername());
            jsonSingle.put("avatarPath", comment.getAvatarPath());
            jsonSingle.put("authorID", comment.getAuthorID());

            jsonArray.add(jsonSingle);
        }
        return  jsonArray;
    }
}
