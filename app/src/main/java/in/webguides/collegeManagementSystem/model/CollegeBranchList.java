package in.webguides.collegeManagementSystem.model;

/**
 * Created by Jerry on 14-06-2017.
 */

public class CollegeBranchList {
    public int collegeBranchId;
    public String collegeBranchName;
    public String collegeBranchAbbr;
    public String collegeCreatedAt;
    public String collegeUpdatedAt;

    public int getCollegeBranchId() {
        return collegeBranchId;
    }

    public void setCollegeBranchId(int collegeBranchId) {
        this.collegeBranchId = collegeBranchId;
    }

    public String getCollegeBranchName() {
        return collegeBranchName;
    }

    public void setCollegeBranchName(String collegeBranchName) {
        this.collegeBranchName = collegeBranchName;
    }

    public String getCollegeBranchAbbr() {
        return collegeBranchAbbr;
    }

    public void setCollegeBranchAbbr(String collegeBranchAbbr) {
        this.collegeBranchAbbr = collegeBranchAbbr;
    }

    public String getCollegeCreatedAt() {
        return collegeCreatedAt;
    }

    public void setCollegeCreatedAt(String collegeCreatedAt) {
        this.collegeCreatedAt = collegeCreatedAt;
    }

    public String getCollegeUpdatedAt() {
        return collegeUpdatedAt;
    }

    public void setCollegeUpdatedAt(String collegeUpdatedAt) {
        this.collegeUpdatedAt = collegeUpdatedAt;
    }
}
