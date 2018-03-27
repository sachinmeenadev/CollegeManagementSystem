package in.webguides.collegeManagementSystem.model;

/**
 * Created by Jerry on 17-06-2017.
 */

public class RoleList {
    private static final String TAG = RoleList.class.getSimpleName();
    private Integer roleId;
    private String roleType;
    private String roleCreatedAt;
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
