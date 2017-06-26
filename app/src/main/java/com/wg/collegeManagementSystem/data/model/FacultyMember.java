package com.wg.collegeManagementSystem.data.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class FacultyMember {
    public static final String TAG = FacultyMember.class.getSimpleName();

    public int facultyMemberId;
    public String facultyMemberName;
    public int facultyMemberBranchId;
    public int facultyMemberCurrentBranchId;
    public String facultyMemberDesignation;
    public String facultyMemberContact;
    public String facultyMemberEmail;

    public int getFacultyMemberId() {
        return facultyMemberId;
    }

    public void setFacultyMemberId(int facultyMemberId) {
        this.facultyMemberId = facultyMemberId;
    }

    public String getFacultyMemberName() {
        return facultyMemberName;
    }

    public void setFacultyMemberName(String facultyMemberName) {
        this.facultyMemberName = facultyMemberName;
    }

    public int getFacultyMemberBranchId() {
        return facultyMemberBranchId;
    }

    public void setFacultyMemberBranchId(int facultyMemberBranchId) {
        this.facultyMemberBranchId = facultyMemberBranchId;
    }

    public int getFacultyMemberCurrentBranchId() {
        return facultyMemberCurrentBranchId;
    }

    public void setFacultyMemberCurrentBranchId(int facultyMemberCurrentBranchId) {
        this.facultyMemberCurrentBranchId = facultyMemberCurrentBranchId;
    }

    public String getFacultyMemberDesignation() {
        return facultyMemberDesignation;
    }

    public void setFacultyMemberDesignation(String facultyMemberDesignation) {
        this.facultyMemberDesignation = facultyMemberDesignation;
    }

    public String getFacultyMemberContact() {
        return facultyMemberContact;
    }

    public void setFacultyMemberContact(String facultyMemberContact) {
        this.facultyMemberContact = facultyMemberContact;
    }

    public String getFacultyMemberEmail() {
        return facultyMemberEmail;
    }

    public void setFacultyMemberEmail(String facultyMemberEmail) {
        this.facultyMemberEmail = facultyMemberEmail;
    }
}
