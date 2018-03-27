package in.webguides.collegeManagementSystem.data.model;

/**
 * Created by Jerry on 18-06-2017.
 */

public class Role {
    private static final String TAG = Role.class.getSimpleName();

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
