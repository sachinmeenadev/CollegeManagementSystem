package com.wg.collegeManagementSystem.data.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class Tutor {
    public static final String TAG = Tutor.class.getSimpleName();

    public int tutorId;
    public int tutorFacultyId;
    public String tutorSection;
    public String tutorBatch;

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public int getTutorFacultyId() {
        return tutorFacultyId;
    }

    public void setTutorFacultyId(int tutorFacultyId) {
        this.tutorFacultyId = tutorFacultyId;
    }

    public String getTutorSection() {
        return tutorSection;
    }

    public void setTutorSection(String tutorSection) {
        this.tutorSection = tutorSection;
    }

    public String getTutorBatch() {
        return tutorBatch;
    }

    public void setTutorBatch(String tutorBatch) {
        this.tutorBatch = tutorBatch;
    }
}
