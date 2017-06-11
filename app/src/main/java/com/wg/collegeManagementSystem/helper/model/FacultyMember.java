package com.wg.collegeManagementSystem.helper.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class FacultyMember {

    public static final String TAG = FacultyMember.class.getSimpleName();
    public static final String TABLE = "facultyMembers";

    // Labels Table Columns names
    public static final String KEY_FacultyMemberId = "facultyMemberId";
    public static final String KEY_FacultyMemberName = "facultyMemberName";
    public static final String KEY_FacultyMemberBranch = "facultyMemberBranch";
    public static final String KEY_FacultyMemberDesignation = "facultyMemberDesignation";

    public String facultyMemberId;
    public String facultyMemberName;
    public String facultyMemberBranch;
    public String facultyMemberDesignation;

    public String getFacultyMemberId() {
        return facultyMemberId;
    }

    public void setFacultyMemberId(String facultyMemberId) {
        this.facultyMemberId = facultyMemberId;
    }

    public String getFacultyMemberName() {
        return facultyMemberName;
    }

    public void setFacultyMemberName(String facultyMemberName) {
        this.facultyMemberName = facultyMemberName;
    }

    public String getFacultyMemberBranch() {
        return facultyMemberBranch;
    }

    public void setFacultyMemberBranch(String facultyMemberBranch) {
        this.facultyMemberBranch = facultyMemberBranch;
    }

    public String getFacultyMemberDesignation() {
        return facultyMemberDesignation;
    }

    public void setFacultyMemberDesignation(String facultyMemberDesignation) {
        this.facultyMemberDesignation = facultyMemberDesignation;
    }
}
