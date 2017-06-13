package com.wg.collegeManagementSystem.data.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class Role {
    public static final String TAG = Role.class.getSimpleName();
    public static final String TABLE = "roles";

    // Labels Table Columns names
    public static final String KEY_RoleId = "roleId";
    public static final String KEY_RoleType = "roleType";

    public int roleId;
    public String roleType;
    public String oldRoleType;
    public String newRoleType;

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

    public String getOldRoleType() {
        return oldRoleType;
    }

    public void setOldRoleType(String oldRoleType) {
        this.oldRoleType = oldRoleType;
    }

    public String getNewRoleType() {
        return newRoleType;
    }

    public void setNewRoleType(String newRoleType) {
        this.newRoleType = newRoleType;
    }
}
