package com.wg.collegeManagementSystem.helper.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class CollegeBranch {

    public static final String TAG = CollegeBranch.class.getSimpleName();
    public static final String TABLE = "collegeBranch";

    // Labels Table Columns names
    public static final String KEY_CollegeBranchId = "collegeBranchId";
    public static final String KEY_CollegeBranchName = "collegeBranchName";

    public String collegeBranchId;
    public String collegeBranchName;

    public String getCollegeBranchId() {
        return collegeBranchId;
    }

    public void setCollegeBranchId(String collegeBranchId) {
        this.collegeBranchId = collegeBranchId;
    }

    public String getCollegeBranchName() {
        return collegeBranchName;
    }

    public void setCollegeBranchName(String collegeBranchName) {
        this.collegeBranchName = collegeBranchName;
    }
}
