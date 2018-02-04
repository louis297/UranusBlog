package UranusBlog.Model;

import java.sql.Date;

public class Account {
    private Integer userId;
    private String username;
    private byte[] password;
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
    private byte[] salt;
    private Integer iters;
    private String description;

    public Account(Integer userId, String username, byte[] password, String firstname, String lastname, String middlename, String email, Date birthday, String nation, String nationFullname, String avatarPath, String avatarThumbnailPath, Integer roleId, String roleDetail, byte[] salt, Integer iters, String description) {
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
        this.salt = salt;
        this.iters = iters;
        this.description = description;
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

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public Integer getIters() {
        return iters;
    }

    public void setIters(Integer iters) {
        this.iters = iters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", nation='" + nation + '\'' +
                ", nationFullname='" + nationFullname + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", avatarThumbnailPath='" + avatarThumbnailPath + '\'' +
                ", roleId=" + roleId +
                ", roleDetail='" + roleDetail + '\'' +
                '}';
    }
}
