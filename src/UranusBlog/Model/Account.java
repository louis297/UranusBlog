package UranusBlog.Model;

import java.sql.Date;

public class Account {
    private Integer userId;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String middlename;
    private String email;
    private Date birthday;
    private String nation;
    private String nationFullname;
    private String avatarPath;
    private String avatarThumbnailPath;
    private Integer roleId;
    private String roleDetail;

    public Account(Integer userId, String username, String password, String firstname, String lastname, String middlename, String email, Date birthday, String nation, String nationFullname, String avatarPath, String avatarThumbnailPath, Integer roleId, String roleDetail) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.email = email;
        this.birthday = birthday;
        this.nation = nation;
        this.nationFullname = nationFullname;
        this.avatarPath = avatarPath;
        this.avatarThumbnailPath = avatarThumbnailPath;
        this.roleId = roleId;
        this.roleDetail = roleDetail;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNationFullname() {
        return nationFullname;
    }

    public void setNationFullname(String nationFullname) {
        this.nationFullname = nationFullname;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getAvatarThumbnailPath() {
        return avatarThumbnailPath;
    }

    public void setAvatarThumbnailPath(String avatarThumbnailPath) {
        this.avatarThumbnailPath = avatarThumbnailPath;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleDetail() {
        return roleDetail;
    }

    public void setRoleDetail(String roleDetail) {
        this.roleDetail = roleDetail;
    }
}
