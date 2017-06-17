package com.wg.collegeManagementSystem.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class Role {
    public static final String TAG = Role.class.getSimpleName();

    public int roleId;
    public String roleType;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

}
