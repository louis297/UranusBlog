package UranusBlog.Model;

import java.sql.Timestamp;

/**
 * data model
 * WARNING: the properties are not exactly from 1 table
 * Please directly see table and SP information from DB
 */
public class Article {
    private Integer articleId;
    private Integer authorId;
    private String title;
    private String content;
    private Timestamp createdTime;
    private Timestamp modifiedTime;
    private Timestamp postTime;
    private Boolean isActive;
    private Boolean isPrivate;
    private String authorName;

    public Article(Integer articleId, Integer authorId, String title, String content, Timestamp createdTime, Timestamp modifiedTime, Timestamp postTime, Boolean isActive, Boolean isPrivate, String authorName) {
        this.articleId = articleId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.postTime = postTime;
        this.isActive = isActive;
        this.isPrivate = isPrivate;
        this.authorName = authorName;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
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

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", authorId=" + authorId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                ", postTime=" + postTime +
                ", isActive=" + isActive +
                ", isPrivate=" + isPrivate +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}
