package com.wg.collegeManagementSystem.model;

/**
 * Created by Jerry on 11-06-2017.
 */

public class Student {
    public static final String TAG = Student.class.getSimpleName();

    // Labels Table Columns names
    public static final String KEY_StudentId = "studentId";
    public static final String KEY_StudentName = "studentName";
    public static final String KEY_StudentRegNumber = "studentRegNumber";
    public static final String KEY_StudentBranchId = "studentBranchId";
    public static final String KEY_StudentSem = "studentSem";
    public static final String KEY_StudentSemSection = "studentSemSection";
    public static final String KEY_StudentSemBatch = "studentSemBatch";
    public static final String KEY_StudentEmail = "studentEmail";
    public static final String KEY_StudentContact = "studentContact";
    public static final String KEY_StudentFatherName = "studentFatherName";
    public static final String KEY_StudentFatherContact = "studentFatherContact";
    public static final String KEY_StudentFatherEmail = "studentFatherEmail";
    public static final String KEY_StudentFatherOccupation = "studentFatherOccupation";
    public static final String KEY_StudentFatherIncome = "studentFatherIncome";
    public static final String KEY_StudentMotherName = "studentMotherName";
    public static final String KEY_StudentMotherContact = "studentMotherContact";
    public static final String KEY_StudentMotherEmail = "studentMotherEmail";
    public static final String KEY_StudentMotherOccupation = "studentMotherOccupation";
    public static final String KEY_StudentMotherIncome = "studentMotherIncome";
    public static final String KEY_StudentLocalGuardianName = "studentLocalGuardianName";
    public static final String KEY_StudentLocalGuardianContact = "studentLocalGuardianContact";
    public static final String KEY_StudentLocalGuardianEmail = "studentLocalGuardianEmail";
    public static final String KEY_StudentResidentType = "studentResidentType";
    public static final String KEY_StudentLocalAddress = "studentLocalAddress";
    public static final String KEY_StudentPermanentAddress = "studentPermanentAddress";
    public static final String KEY_StudentCity = "studentCity";
    public static final String KEY_StudentState = "studentState";
    public static final String KEY_StudentCountry = "studentCountry";
    public static final String KEY_StudentPinCode = "studentPinCode";

    public int studentId;
    public String studentName;
    public String studentRegNumber;
    public int studentBranchId;
    public String studentSem;
    public String studentSemSection;
    public String studentSemBatch;
    public String studentEmail;
    public String studentContact;
    public String studentFatherName;
    public String studentFatherContact;
    public String studentFatherEmail;
    public String studentFatherOccupation;
    public String studentFatherIncome;
    public String studentMotherName;
    public String studentMotherContact;
    public String studentMotherEmail;
    public String studentMotherOccupation;
    public String studentMotherIncome;
    public String studentLocalGuardianName;
    public String studentLocalGuardianContact;
    public String studentLocalGuardianEmail;
    public String studentResidentType;
    public String studentLocalAddress;
    public String studentPermanentAddress;
    public String studentCity;
    public String studentState;
    public String studentCountry;
    public String studentPinCode;

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

    public int getStudentBranch() {
        return studentBranchId;
    }

    public void setStudentBranch(int studentBranchId) {
        this.studentBranchId = studentBranchId;
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

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentContact() {
        return studentContact;
    }

    public void setStudentContact(String studentContact) {
        this.studentContact = studentContact;
    }

    public String getStudentFatherName() {
        return studentFatherName;
    }

    public void setStudentFatherName(String studentFatherName) {
        this.studentFatherName = studentFatherName;
    }

    public String getStudentFatherContact() {
        return studentFatherContact;
    }

    public void setStudentFatherContact(String studentFatherContact) {
        this.studentFatherContact = studentFatherContact;
    }

    public String getStudentFatherEmail() {
        return studentFatherEmail;
    }

    public void setStudentFatherEmail(String studentFatherEmail) {
        this.studentFatherEmail = studentFatherEmail;
    }

    public String getStudentFatherOccupation() {
        return studentFatherOccupation;
    }

    public void setStudentFatherOccupation(String studentFatherOccupation) {
        this.studentFatherOccupation = studentFatherOccupation;
    }

    public String getStudentFatherIncome() {
        return studentFatherIncome;
    }

    public void setStudentFatherIncome(String studentFatherIncome) {
        this.studentFatherIncome = studentFatherIncome;
    }

    public String getStudentMotherName() {
        return studentMotherName;
    }

    public void setStudentMotherName(String studentMotherName) {
        this.studentMotherName = studentMotherName;
    }

    public String getStudentMotherContact() {
        return studentMotherContact;
    }

    public void setStudentMotherContact(String studentMotherContact) {
        this.studentMotherContact = studentMotherContact;
    }

    public String getStudentMotherEmail() {
        return studentMotherEmail;
    }

    public void setStudentMotherEmail(String studentMotherEmail) {
        this.studentMotherEmail = studentMotherEmail;
    }

    public String getStudentMotherOccupation() {
        return studentMotherOccupation;
    }

    public void setStudentMotherOccupation(String studentMotherOccupation) {
        this.studentMotherOccupation = studentMotherOccupation;
    }

    public String getStudentMotherIncome() {
        return studentMotherIncome;
    }

    public void setStudentMotherIncome(String studentMotherIncome) {
        this.studentMotherIncome = studentMotherIncome;
    }

    public String getStudentLocalGuardianName() {
        return studentLocalGuardianName;
    }

    public void setStudentLocalGuardianName(String studentLocalGuardianName) {
        this.studentLocalGuardianName = studentLocalGuardianName;
    }

    public String getStudentLocalGuardianContact() {
        return studentLocalGuardianContact;
    }

    public void setStudentLocalGuardianContact(String studentLocalGuardianContact) {
        this.studentLocalGuardianContact = studentLocalGuardianContact;
    }

    public String getStudentLocalGuardianEmail() {
        return studentLocalGuardianEmail;
    }

    public void setStudentLocalGuardianEmail(String studentLocalGuardianEmail) {
        this.studentLocalGuardianEmail = studentLocalGuardianEmail;
    }

    public String getStudentResidentType() {
        return studentResidentType;
    }

    public void setStudentResidentType(String studentResidentType) {
        this.studentResidentType = studentResidentType;
    }

    public String getStudentLocalAddress() {
        return studentLocalAddress;
    }

    public void setStudentLocalAddress(String studentLocalAddress) {
        this.studentLocalAddress = studentLocalAddress;
    }

    public String getStudentPermanentAddress() {
        return studentPermanentAddress;
    }

    public void setStudentPermanentAddress(String studentPermanentAddress) {
        this.studentPermanentAddress = studentPermanentAddress;
    }

    public String getStudentCity() {
        return studentCity;
    }

    public void setStudentCity(String studentCity) {
        this.studentCity = studentCity;
    }

    public String getStudentState() {
        return studentState;
    }

    public void setStudentState(String studentState) {
        this.studentState = studentState;
    }

    public String getStudentCountry() {
        return studentCountry;
    }

    public void setStudentCountry(String studentCountry) {
        this.studentCountry = studentCountry;
    }

    public String getStudentPinCode() {
        return studentPinCode;
    }

    public void setStudentPinCode(String studentPinCode) {
        this.studentPinCode = studentPinCode;
    }
}
