package com.wg.collegeManagementSystem.data.model;

/**
 * Created by Jerry on 12-09-2017.
 */

public class StudentSearch {
    public static final String TAG = StudentSearch.class.getSimpleName();

    public int studentId;
    public String studentName;
    public String studentRegNumber;
    public String studentBranch;
    public String studentSem;
    public String studentSemSection;
    public String studentSemBatch;
    public String studentContact;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRegNumber() {
        return studentRegNumber;
    }

    public void setStudentRegNumber(String studentRegNumber) {
        this.studentRegNumber = studentRegNumber;
    }

    public String getStudentBranch() {
        return studentBranch;
    }

    public void setStudentBranch(String studentBranch) {
        this.studentBranch = studentBranch;
    }

    public String getStudentSem() {
        return studentSem;
    }

    public void setStudentSem(String studentSem) {
        this.studentSem = studentSem;
    }

    public String getStudentSemSection() {
        return studentSemSection;
    }

    public void setStudentSemSection(String studentSemSection) {
        this.studentSemSection = studentSemSection;
    }

    public String getStudentSemBatch() {
        return studentSemBatch;
    }

    public void setStudentSemBatch(String studentSemBatch) {
        this.studentSemBatch = studentSemBatch;
    }

    public String getStudentContact() {
        return studentContact;
    }

    public void setStudentContact(String studentContact) {
        this.studentContact = studentContact;
    }
}
