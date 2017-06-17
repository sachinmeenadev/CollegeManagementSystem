package com.wg.collegeManagementSystem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jerry on 17-06-2017.
 */

public class Role {

    @SerializedName("roleId")
    @Expose
    private Integer roleId;
    @SerializedName("roleType")
    @Expose
    private String roleType;
    @SerializedName("roleCreatedAt")
    @Expose
    private String roleCreatedAt;
    @SerializedName("roleUpdatedAt")
    @Expose
    private String roleUpdatedAt;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleCreatedAt() {
        return roleCreatedAt;
    }

    public void setRoleCreatedAt(String roleCreatedAt) {
        this.roleCreatedAt = roleCreatedAt;
    }

    public String getRoleUpdatedAt() {
        return roleUpdatedAt;
    }

    public void setRoleUpdatedAt(String roleUpdatedAt) {
        this.roleUpdatedAt = roleUpdatedAt;
    }
}
