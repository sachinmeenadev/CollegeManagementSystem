package com.wg.collegeManagementSystem.data.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class Subject {

    public static final String TAG = Subject.class.getSimpleName();

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
