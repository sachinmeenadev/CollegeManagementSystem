package in.webguides.collegeManagementSystem.model;

/**
 * Created by Jerry on 09-07-2017.
 */

public class TutorList {
    public int facultyMemberId;
    public String facultyMemberName;
    public int facultyMemberBranchId;
    public int facultyMemberCurrentBranchId;
    public String facultyMemberDesignation;
    public String facultyMemberContact;
    public String facultyMemberEmail;
    public String facultyMemberCreatedAt;
    public String facultyMemberUpdatedAt;
    public int tutorId;
    public int tutorFacultyId;
    public String tutorClass;
    public String tutorSection;
    public String tutorBatch;

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

    public int getFacultyMemberCurrentBranchId() {
        return facultyMemberCurrentBranchId;
    }

    public void setFacultyMemberCurrentBranchId(int facultyMemberCurrentBranchId) {
        this.facultyMemberCurrentBranchId = facultyMemberCurrentBranchId;
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

    public String getFacultyMemberCreatedAt() {
        return facultyMemberCreatedAt;
    }

    public void setFacultyMemberCreatedAt(String facultyMemberCreatedAt) {
        this.facultyMemberCreatedAt = facultyMemberCreatedAt;
    }

    public String getFacultyMemberUpdatedAt() {
        return facultyMemberUpdatedAt;
    }

    public void setFacultyMemberUpdatedAt(String facultyMemberUpdatedAt) {
        this.facultyMemberUpdatedAt = facultyMemberUpdatedAt;
    }

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

    public String getTutorClass() {
        return tutorClass;
    }

    public void setTutorClass(String tutorClass) {
        this.tutorClass = tutorClass;
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
