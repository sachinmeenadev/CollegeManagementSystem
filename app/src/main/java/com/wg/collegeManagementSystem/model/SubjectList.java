package com.wg.collegeManagementSystem.model;

/**
 * Created by Jerry on 14-06-2017.
 */

public class SubjectList {
    public int subjectId;
    public String subjectName;
    public String subjectAbbr;
    public String subjectCode;

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
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
