package com.wg.collegeManagementSystem.helper.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class Role {
    public static final String TAG = Role.class.getSimpleName();
    public static final String TABLE = "roles";

    // Labels Table Columns names
    public static final String KEY_RoleId = "roleId";
    public static final String KEY_RoleType = "roleType";

    public String roleId;
    public String roleType;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
