package in.webguides.collegeManagementSystem.data.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class CollegeBranch {
    public static final String TAG = CollegeBranch.class.getSimpleName();

    public int collegeBranchId;
    public String collegeBranchName;
    public String collegeBranchAbbr;

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
}
