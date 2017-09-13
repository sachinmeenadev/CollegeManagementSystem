package com.wg.collegeManagementSystem.data.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class FacultyMemberAllotClass {
    public static final String TAG = FacultyMemberAllotClass.class.getSimpleName();

    public int fmsId;
    public int fmsFacultyId;
    public int fmsSubjectId;
    public String fmsClass;
    public String fmsSection;
    public String fmsBatch;
    public String facultyMemberName;
    public String subjectName;
    public String subjectAbbr;
    public String subjectCode;

    public int getFmsId() {
        return fmsId;
    }

    public void setFmsId(int fmsId) {
        this.fmsId = fmsId;
    }

    public int getFmsFacultyId() {
        return fmsFacultyId;
    }

    public void setFmsFacultyId(int fmsFacultyId) {
        this.fmsFacultyId = fmsFacultyId;
    }

    public int getFmsSubjectId() {
        return fmsSubjectId;
    }

    public void setFmsSubjectId(int fmsSubjectId) {
        this.fmsSubjectId = fmsSubjectId;
    }

    public String getFmsClass() {
        return fmsClass;
    }

    public void setFmsClass(String fmsClass) {
        this.fmsClass = fmsClass;
    }

    public String getFmsSection() {
        return fmsSection;
    }

    public void setFmsSection(String fmsSection) {
        this.fmsSection = fmsSection;
    }

    public String getFmsBatch() {
        return fmsBatch;
    }

    public void setFmsBatch(String fmsBatch) {
        this.fmsBatch = fmsBatch;
    }

    public String getFacultyMemberName() {
        return facultyMemberName;
    }

    public void setFacultyMemberName(String facultyMemberName) {
        this.facultyMemberName = facultyMemberName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectAbbr() {
        return subjectAbbr;
    }

    public void setSubjectAbbr(String subjectAbbr) {
        this.subjectAbbr = subjectAbbr;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
}
