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
import java.util.Properties;

public class ArticleModifyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        //String articleid= req.getParameter("articleid");
        //String userId=req.getParameter("userId");
        Integer userId=2;
        Integer articleid=2;

        System.out.println("after int delcaration");

        String title= "hello louis this is the test for article modifier";
        String content="Hello!";
        String postTimeStr="2018-01-24 20:29:42";
        String isPrivateStr="false";


        try (ArticleDAO dao = new ArticleDAO(new MySQLDatabase(getServletContext()))) {
            // 1. check if the user can modify the article
                // TODO: as above

            // 2. validate the input data
            if(title == null || title.isEmpty() ||
                    content == null || content.isEmpty() ||
                    postTimeStr == null || postTimeStr.isEmpty() ||
                    isPrivateStr == null || isPrivateStr.isEmpty()){
                out.print("{result:\"fail\"}");
                return;
            }

            Timestamp postTime = Timestamp.valueOf(postTimeStr);
            boolean modifiedPrivacy= Boolean.parseBoolean(isPrivateStr);

            // 3. do modify
            dao.updateArticle(articleid, title, content, postTime, modifiedPrivacy);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //String modifiedTitle= req.getParameter("newtitle");
//        String modifiedContent=req.getParameter("newcontent");
//        String modifiedPostTime=req.getParameter("newposttime");
//        int modifiedPrivacy=Integer.parseInt(req.getParameter("privacy"));

//        System.out.println("after delcaration ");
//        System.out.println(""+modifiedContent+modifiedPostTime);
//    // Test Printer
//        PrintWriter out = resp.getWriter();
//        System.out.println("a1");
//        try (Connection conn1 = DriverManager.getConnection(dbProps.getProperty("url"), dbProps.getProperty("user"), dbProps.getProperty("password"))) {
//            System.out.println("124");
//            try (PreparedStatement stmt = conn1.prepareStatement("call GetArticleById (?,?)")) {
//                stmt.setInt(1, userId);
//                stmt.setInt(2, articleid);
//                try (ResultSet r = stmt.executeQuery()) {
//                    while (r.next()) {
//                        if (modifiedTitle.equals(null) || modifiedTitle.equals("")){
//                            modifiedTitle=r.getString(3);
//                            System.out.println("1");
//                        }
//                        if (modifiedContent.equals(null) || modifiedContent.equals("")){
//                            modifiedContent=r.getString(4);
//                        }
//                        if (modifiedPostTime.equals(null) || modifiedPostTime.equals("")){
//                            modifiedContent=r.getString(7);
//                        }
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }

 }
