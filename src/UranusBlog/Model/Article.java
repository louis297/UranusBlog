package UranusBlog.Model;

import java.sql.Timestamp;

/**
 * data model
 * WARNING: the properties are not exactly from 1 table
 * Please directly see table and SP information from DB
 */
public class Article {
    private Integer article_id;
    private Integer author_id;
    private String title;
    private String content;
    private Timestamp created_time;
    private Timestamp modified_time;
    private Timestamp post_time;
    private Boolean is_private;
    private String author_name;

    public Article(Integer article_id, Integer author_id, String title, String content, Timestamp created_time, Timestamp modified_time, Timestamp post_time, Boolean is_private, String author_name) {
        this.article_id = article_id;
        this.author_id = author_id;
        this.title = title;
        this.content = content;
        this.created_time = created_time;
        this.modified_time = modified_time;
        this.post_time = post_time;
        this.is_private = is_private;
        this.author_name = author_name;
    }

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Timestamp created_time) {
        this.created_time = created_time;
    }

    public Timestamp getModified_time() {
        return modified_time;
    }

    public void setModified_time(Timestamp modified_time) {
        this.modified_time = modified_time;
    }

    public Timestamp getPost_time() {
        return post_time;
    }

    public void setPost_time(Timestamp post_time) {
        this.post_time = post_time;
    }

    public Boolean getIs_private() {
        return is_private;
    }

    public void setIs_private(Boolean is_private) {
        this.is_private = is_private;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }
}
