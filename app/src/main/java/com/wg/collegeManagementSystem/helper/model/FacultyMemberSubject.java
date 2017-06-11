package com.wg.collegeManagementSystem.helper.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class FacultyMemberSubject {
    public static final String TAG = FacultyMemberSubject.class.getSimpleName();
    public static final String TABLE = "facultyMemberSubjects";

    // Labels Table Columns names
    public static final String KEY_FMSId = "fmsId";
    public static final String KEY_FMSFacultyId = "fmsFacultyId";
    public static final String KEY_FMSSubjectId = "fmsSubjectId";

    public String fmsId;
    public String fmsFacultyId;
    public String fmsSubjectId;

    public String getFmsId() {
        return fmsId;
    }

    public void setFmsId(String fmsId) {
        this.fmsId = fmsId;
    }

    public String getFmsFacultyId() {
        return fmsFacultyId;
    }

    public void setFmsFacultyId(String fmsFacultyId) {
        this.fmsFacultyId = fmsFacultyId;
    }

    public String getFmsSubjectId() {
        return fmsSubjectId;
    }

    public void setFmsSubjectId(String fmsSubjectId) {
        this.fmsSubjectId = fmsSubjectId;
    }
}
