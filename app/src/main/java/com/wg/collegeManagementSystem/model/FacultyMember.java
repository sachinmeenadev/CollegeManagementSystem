package com.wg.collegeManagementSystem.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class FacultyMember {

    public static final String TAG = FacultyMember.class.getSimpleName();

    // Labels Table Columns names
    public static final String KEY_FacultyMemberId = "facultyMemberId";
    public static final String KEY_FacultyMemberName = "facultyMemberName";
    public static final String KEY_FacultyMemberBranchId = "facultyMemberBranchId";
    public static final String KEY_FacultyMemberDesignation = "facultyMemberDesignation";
    public static final String KEY_FacultyMemberContact = "facultyMemberContact";
    public static final String KEY_FacultyMemberEmail = "facultyMemberEmail";

    public int facultyMemberId;
    public String facultyMemberName;
    public int facultyMemberBranchId;
    public String facultyMemberDesignation;
    public String facultyMemberContact;
    public String facultyMemberEmail;
    public String oldFacultyMemberName;
    public String oldFacultyMemberDesignation;
    public String oldFacultyMemberContact;
    public String oldFacultyMemberEmail;
    public String newFacultyMemberName;
    public String newFacultyMemberDesignation;
    public String newFacultyMemberContact;
    public String newFacultyMemberEmail;

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

    public String getOldFacultyMemberName() {
        return oldFacultyMemberName;
    }

    public void setOldFacultyMemberName(String oldFacultyMemberName) {
        this.oldFacultyMemberName = oldFacultyMemberName;
    }

    public String getOldFacultyMemberDesignation() {
        return oldFacultyMemberDesignation;
    }

    public void setOldFacultyMemberDesignation(String oldFacultyMemberDesignation) {
        this.oldFacultyMemberDesignation = oldFacultyMemberDesignation;
    }

    public String getOldFacultyMemberContact() {
        return oldFacultyMemberContact;
    }

    public void setOldFacultyMemberContact(String oldFacultyMemberContact) {
        this.oldFacultyMemberContact = oldFacultyMemberContact;
    }

    public String getOldFacultyMemberEmail() {
        return oldFacultyMemberEmail;
    }

    public void setOldFacultyMemberEmail(String oldFacultyMemberEmail) {
        this.oldFacultyMemberEmail = oldFacultyMemberEmail;
    }

    public String getNewFacultyMemberName() {
        return newFacultyMemberName;
    }

    public void setNewFacultyMemberName(String newFacultyMemberName) {
        this.newFacultyMemberName = newFacultyMemberName;
    }

    public String getNewFacultyMemberDesignation() {
        return newFacultyMemberDesignation;
    }

    public void setNewFacultyMemberDesignation(String newFacultyMemberDesignation) {
        this.newFacultyMemberDesignation = newFacultyMemberDesignation;
    }

    public String getNewFacultyMemberContact() {
        return newFacultyMemberContact;
    }

    public void setNewFacultyMemberContact(String newFacultyMemberContact) {
        this.newFacultyMemberContact = newFacultyMemberContact;
    }

    public String getNewFacultyMemberEmail() {
        return newFacultyMemberEmail;
    }

    public void setNewFacultyMemberEmail(String newFacultyMemberEmail) {
        this.newFacultyMemberEmail = newFacultyMemberEmail;
    }
}
