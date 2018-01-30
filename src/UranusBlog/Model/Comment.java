package UranusBlog.Model;

import java.sql.Timestamp;

//c.content, c.created_time, a.username, a.avatar_path
public class Comment {
    private String content;
    private Timestamp createdTime;
    private boolean isActive;
    private Integer authorID;
    private String username;
    private String avatarPath;


    public Comment(String content, Timestamp createdTime, boolean isActive, Integer authorID, String username, String avatarPath) {
        this.content = content;
        this.createdTime = createdTime;
        this.isActive = isActive;
        this.authorID = authorID;
        this.username = username;
        this.avatarPath = avatarPath;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Integer getAuthorID() {
        return authorID;
    }

    public void setAuthorID(Integer authorID) {
        this.authorID = authorID;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", createdTime=" + createdTime +
                ", username='" + username + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
