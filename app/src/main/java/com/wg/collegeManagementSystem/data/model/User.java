package com.wg.collegeManagementSystem.data.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class User {
    public static final String TAG = User.class.getSimpleName();

    public int userId;
    public String userName;
    public String userEmail;
    public int userRoleId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }
}
