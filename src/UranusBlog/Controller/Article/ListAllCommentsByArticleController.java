package UranusBlog.Controller.Article;

import UranusBlog.Model.Article;
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
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ListAllCommentsByArticleController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int articleid=Integer.parseInt(req.getParameter("articleid").toString());
        System.out.println("entered servelet");
        System.out.println("articleid"+articleid);
        List<Comment> comments= new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties dbProps = new Properties();
        dbProps.setProperty("url", "jdbc:mysql://db.sporadic.nz/xliu617");
        dbProps.setProperty("user", "xliu617");
        dbProps.setProperty("password", "123");
        try (Connection conn = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM comment WHERE article_id = ? ;")) {
                stmt.setInt(1, articleid);
                try (ResultSet r = stmt.executeQuery()) {
                    while (r.next()){
                        int cid= r.getInt (1);
                        int articid= r.getInt (2);
                        int authid=r.getInt(3);
                        System.out.println("authid"+authid);
                        String authorusername="";
                        String avatar_path="";
                        try (PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM account WHERE uid = ? ;")) {
                            System.out.println("second statement entered");
                            stmt2.setInt(1,authid);
                            try(ResultSet rs=stmt2.executeQuery()) {
                                while(rs.next()){
                                    System.out.println("second query entered");
                                    authorusername=rs.getString(2);
                                    System.out.println("userame"+authorusername);
                                    avatar_path=rs.getString(10);
                                    System.out.println("path"+avatar_path);
                                }
                            }
                        }
                        String content=r.getString(4);
                        Timestamp createtime= r.getTimestamp(5);
                        int active = r.getInt(r.findColumn("active"));
                        boolean acitveBoolean = false;
                        if (active == 0) {
                            acitveBoolean = false;
                        } else if (active == 1) {
                            acitveBoolean = true;
                        }
                        Comment single=new Comment(cid,content,createtime,acitveBoolean,authid,authorusername,avatar_path,articleid);
                        System.out.println(single.toString());
                        comments.add(single);

                        }
                    }
                }
            } catch (SQLException e) {
            e.printStackTrace();
            }

            PrintWriter out = resp.getWriter();

            try {
                JSONArray jsonArray = constructJSON(comments);
                System.out.println(jsonArray);
                if (jsonArray.isEmpty()) {
                    out.print("{\"result\":\"fail\", \"reason\":\"No article found\"}");
                    System.out.println("json not returned");
                } else {
                    out.print(jsonArray);
                    System.out.println("json returned");
                }
            } catch (SQLException f) {
                System.out.println("exception");
                f.printStackTrace();
            }

        }



    private JSONArray constructJSON (List <Comment> comments) throws SQLException {
        JSONArray jsonArray = new JSONArray();
        for (Comment comment : comments) {

            JSONObject jsonSingle = new JSONObject();

            jsonSingle.put("comment_id", comment.getCommentID());
            jsonSingle.put("content", comment.getContent());
            jsonSingle.put("active", comment.isActive());
            jsonSingle.put("authorname", comment.getUsername());
            System.out.println("test 11"+comment.getCommentID());

            jsonArray.add(jsonSingle);
        }
        return jsonArray;
    }
}
