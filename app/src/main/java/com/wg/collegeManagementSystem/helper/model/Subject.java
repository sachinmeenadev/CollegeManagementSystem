package com.wg.collegeManagementSystem.helper.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class Subject {

    public static final String TAG = Subject.class.getSimpleName();
    public static final String TABLE = "subjects";

    // Labels Table Columns names
    public static final String KEY_SubjectId = "subjectId";
    public static final String KEY_SubjectName = "subjectName";
    public static final String KEY_SubjectAbbr = "subjectAbbr";
    public static final String KEY_SubjectCode = "subjectCode";

    public String subjectId;
    public String subjectName;
    public String subjectAbbr;
    public String subjectCode;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
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
