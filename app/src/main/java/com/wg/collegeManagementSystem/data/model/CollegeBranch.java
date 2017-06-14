package com.wg.collegeManagementSystem.data.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class CollegeBranch {

    public static final String TAG = CollegeBranch.class.getSimpleName();
    public static final String TABLE = "collegeBranches";

    // Labels Table Columns names
    public static final String KEY_CollegeBranchId = "collegeBranchId";
    public static final String KEY_CollegeBranchName = "collegeBranchName";
    public static final String KEY_CollegeBranchAbbr = "collegeBranchAbbr";

    public int collegeBranchId;
    public String collegeBranchName;
    public String collegeBranchAbbr;
    public String oldCollegeBranchName;
    public String oldCollegeBranchAbbr;
    public String newCollegeBranchName;
    public String newCollegeBranchAbbr;

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

    public String getOldCollegeBranchName() {
        return oldCollegeBranchName;
    }

    public void setOldCollegeBranchName(String oldCollegeBranchName) {
        this.oldCollegeBranchName = oldCollegeBranchName;
    }

    public String getOldCollegeBranchAbbr() {
        return oldCollegeBranchAbbr;
    }

    public void setOldCollegeBranchAbbr(String oldCollegeBranchAbbr) {
        this.oldCollegeBranchAbbr = oldCollegeBranchAbbr;
    }

    public String getNewCollegeBranchName() {
        return newCollegeBranchName;
    }

    public void setNewCollegeBranchName(String newCollegeBranchName) {
        this.newCollegeBranchName = newCollegeBranchName;
    }

    public String getNewCollegeBranchAbbr() {
        return newCollegeBranchAbbr;
    }

    public void setNewCollegeBranchAbbr(String newCollegeBranchAbbr) {
        this.newCollegeBranchAbbr = newCollegeBranchAbbr;
    }
}
