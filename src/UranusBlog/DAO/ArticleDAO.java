package UranusBlog.DAO;

import UranusBlog.DB.Database;
import UranusBlog.Model.Article;
import UranusBlog.Model.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO implements AutoCloseable{

    private final Connection conn;

    /**
     * Creates a new DAO with a {@link Connection} from the given {@link Database}.
     *
     * @param db the {@link Database} from which to establish a {@link Connection}
     * @throws SQLException if something went wrong.
     */
    public ArticleDAO(Database db) throws SQLException {
        this.conn = db.getConnection();
    }

    public List<Comment> getComments(int commentID, int userID) throws SQLException{
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("call GetCommentList(?,?)")) {
            stmt.setInt(1, commentID);
            stmt.setInt(2, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){
                    comments.add(commentFromResultSet(rs));
                }
            }
        }
        return comments;
    }

    public Comment getCommentById(int commentID) throws SQLException{
        try (PreparedStatement stmt = conn.prepareStatement("call GetCommentById (?)")) {
            stmt.setInt(1, commentID);
            try (ResultSet r = stmt.executeQuery()) {
                if(r.next()){
                    return commentFromResultSet(r);
                } else {
                    return null;
                }
            }
        }
    }

    public List<Article> getArticles(int userID, int start, int amount, boolean own) throws SQLException {
        String query;
        if(own){
            query = "CALL GetArticleListOwn (?,?,?);";
        } else {
            query = "CALL GetArticleListAll (?,?,?);";
        }
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, start);
            stmt.setInt(3, amount);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Article> articles = new ArrayList<>();
                while (rs.next()) {
                    articles.add(articleFromResultSet(rs));
                }
                return articles;
            }
        }
    }

    public Article getArticleById(int userID, int articleID) throws SQLException{
        try (PreparedStatement stmt = conn.prepareStatement("call GetArticleById (?,?)")) {
            stmt.setInt(1, userID);
            stmt.setInt(2, articleID);
            try (ResultSet r = stmt.executeQuery()) {
                if(r.next()){
                    return articleFromResultSet(r);
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * VERY IMPORTANT
     * need validate if the user is the author or the user is admin!
     * the DAO method or SP will not do any validation!
     *
     * @param articleID
     * @throws SQLException
     */
    public void deleteArticle(int articleID) throws SQLException{
        try (PreparedStatement stmt = conn.prepareStatement("call DeleteArticle (?)")) {
            stmt.setInt(1, articleID);
            stmt.executeQuery();
        }
    }

    /**
     * VERY IMPORTANT
     * need validate if the user is the author or the user is admin!
     * the DAO method or SP will not do any validation!
     *
     * @param commentID
     * @throws SQLException
     */
    public void deleteComment(int commentID) throws SQLException{
        try (PreparedStatement stmt = conn.prepareStatement("call DeleteComment (?)")) {
            stmt.setInt(1, commentID);
            stmt.executeQuery();
        }
    }

    public void addComment(int userID, int articleID, String content) throws SQLException{
        String query = "call InsertComment(?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, articleID);
            stmt.setString(3, content);
            stmt.executeQuery();
        }
    }

    public void resumeComment(int commentID) throws SQLException{
        try (PreparedStatement stmt = conn.prepareStatement("call ResumeComment (?)")) {
            stmt.setInt(1, commentID);
            stmt.executeQuery();
        }
    }

    /**
     * VERY IMPORTANT
     * need validate if the user is the author or the user is admin!
     * the DAO method or SP will not do any validation!
     *
     * @param articleID
     * @param title
     * @param content
     * @param postTime
     * @param isPrivate
     * @throws SQLException
     */
    public int updateArticle(int articleID, String title, String content, Timestamp postTime, Boolean isPrivate) throws SQLException{
        String query = "call UpdateArticle (?,?,?,?,?)";
        return modifyArticle(articleID, title, content, postTime, isPrivate, query);
    }

    public int addArticle(int userID, String title, String content, Timestamp postTime, Boolean isPrivate) throws SQLException{
        String query = "call InsertArticle(?,?,?,?,?)";
        return modifyArticle(userID, title, content, postTime, isPrivate, query);
    }

    public void resumeArticle(int articleID) throws SQLException{
        try (PreparedStatement stmt = conn.prepareStatement("call ResumeArticle (?)")) {
            stmt.setInt(1, articleID);
            stmt.executeQuery();
        }
    }

    /**
     *
     * @param ID userID for add, or articleID for update
     * @param title
     * @param content
     * @param postTime
     * @param isPrivate
     * @param query
     * @throws SQLException
     */
    private int modifyArticle (int ID, String title, String content, Timestamp postTime, Boolean isPrivate, String query) throws SQLException{
        Integer intPrivate;
        if(isPrivate){
            intPrivate = 1;
        } else {
            intPrivate = 0;
        }
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ID);
            stmt.setString(2, title);
            stmt.setString(3, content);
            stmt.setTimestamp(4, postTime);
            stmt.setInt(5, intPrivate);
            try (ResultSet r = stmt.executeQuery()){
                if(r.next()){
                    return r.getInt(1);
                } else {
                    return 0;
                }
            }
        }
    }

    private Article articleFromResultSet(ResultSet rs) throws SQLException {
        Boolean is_private = rs.getInt(9) == 1;
        Boolean is_active = rs.getInt(8) == 1;
        return new Article(rs.getInt(1), rs.getInt(2), rs.getString(3),
                rs.getString(4), rs.getTimestamp(5), rs.getTimestamp(6),
                rs.getTimestamp(7), is_active, is_private, rs.getString(10));
    }

    private Comment commentFromResultSet(ResultSet r) throws SQLException {
        Boolean is_active = r.getInt(4) == 1;
        return new Comment(r.getInt(1), r.getString(2), r.getTimestamp(3), is_active,
                r.getInt(5), r.getString(6), r.getString(7));
    }

    @Override
    public void close() throws Exception {
        conn.close();
    }
}
