package com.wg.collegeManagementSystem.data.model;

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

    public int subjectId;
    public String subjectName;
    public String subjectAbbr;
    public String subjectCode;
    public String oldSubjectName;
    public String oldSubjectAbbr;
    public String oldSubjectCode;
    public String newSubjectName;
    public String newSubjectAbbr;
    public String newSubjectCode;

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

    public String getOldSubjectName() {
        return oldSubjectName;
    }

    public void setOldSubjectName(String oldSubjectName) {
        this.oldSubjectName = oldSubjectName;
    }

    public String getOldSubjectAbbr() {
        return oldSubjectAbbr;
    }

    public void setOldSubjectAbbr(String oldSubjectAbbr) {
        this.oldSubjectAbbr = oldSubjectAbbr;
    }

    public String getOldSubjectCode() {
        return oldSubjectCode;
    }

    public void setOldSubjectCode(String oldSubjectCode) {
        this.oldSubjectCode = oldSubjectCode;
    }

    public String getNewSubjectName() {
        return newSubjectName;
    }

    public void setNewSubjectName(String newSubjectName) {
        this.newSubjectName = newSubjectName;
    }

    public String getNewSubjectAbbr() {
        return newSubjectAbbr;
    }

    public void setNewSubjectAbbr(String newSubjectAbbr) {
        this.newSubjectAbbr = newSubjectAbbr;
    }

    public String getNewSubjectCode() {
        return newSubjectCode;
    }

    public void setNewSubjectCode(String newSubjectCode) {
        this.newSubjectCode = newSubjectCode;
    }
}
