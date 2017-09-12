package com.wg.collegeManagementSystem.data.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class FacultyMemberSubject {
    public static final String TAG = FacultyMemberSubject.class.getSimpleName();

    public int fmsId;
    public int fmsFacultyId;
    public int fmsSubjectId;

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
}
