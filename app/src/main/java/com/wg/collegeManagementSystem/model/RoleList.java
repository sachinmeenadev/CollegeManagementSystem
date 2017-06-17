package com.wg.collegeManagementSystem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jerry on 17-06-2017.
 */

public class RoleList {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("roles")
    @Expose
    private List<Role> roles = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
