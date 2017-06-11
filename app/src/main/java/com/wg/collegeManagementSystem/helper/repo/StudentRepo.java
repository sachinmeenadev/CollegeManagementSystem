package com.wg.collegeManagementSystem.helper.repo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.wg.collegeManagementSystem.helper.DatabaseManager;
import com.wg.collegeManagementSystem.helper.model.Student;

/**
 * Created by Jerry on 11-06-2017.
 */

public class StudentRepo {
    private Student student;

    public StudentRepo() {
        student = new Student();
    }

    public static String createTable() {
        return "CREATE TABLE " + Student.TABLE + "("
                + Student.KEY_StudentId + " INT  PRIMARY KEY AUTOINCREMENT,"
                + Student.KEY_StudentName + " VARCHAR,"
                + Student.KEY_StudentRegNumber + " VARCHAR,"
                + Student.KEY_StudentBranch + " VARCHAR,"
                + Student.KEY_StudentSem + " VARCHAR,"
                + Student.KEY_StudentSemSection + " VARCHAR,"
                + Student.KEY_StudentSemBatch + " VARCHAR,"
                + Student.KEY_StudentEmail + " VARCHAR,"
                + Student.KEY_StudentContact + " VARCHAR,"
                + Student.KEY_StudentFatherName + " VARCHAR,"
                + Student.KEY_StudentFatherContact + " VARCHAR,"
                + Student.KEY_StudentFatherEmail + " VARCHAR,"
                + Student.KEY_StudentFatherOccupation + " VARCHAR,"
                + Student.KEY_StudentFatherIncome + " VARCHAR,"
                + Student.KEY_StudentMotherName + " VARCHAR,"
                + Student.KEY_StudentMotherContact + " VARCHAR,"
                + Student.KEY_StudentMotherEmail + " VARCHAR,"
                + Student.KEY_StudentMotherOccupation + " VARCHAR,"
                + Student.KEY_StudentMotherIncome + " VARCHAR,"
                + Student.KEY_StudentLocalGuardianName + " VARCHAR,"
                + Student.KEY_StudentLocalGuardianContact + " VARCHAR,"
                + Student.KEY_StudentLocalGuardianEmail + " VARCHAR,"
                + Student.KEY_StudentResidentType + " VARCHAR,"
                + Student.KEY_StudentLocalAddress + " VARCHAR,"
                + Student.KEY_StudentPermanentAddress + " VARCHAR,"
                + Student.KEY_StudentCity + " VARCHAR,"
                + Student.KEY_StudentState + " VARCHAR,"
                + Student.KEY_StudentCountry + " VARCHAR,"
                + Student.KEY_StudentPinCode + " VARCHAR)";
    }

    public int insert(Student student) {
        int studentId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_StudentRegNumber, student.getStudentRegNumber());
        values.put(Student.KEY_StudentBranch, student.getStudentBranch());
        values.put(Student.KEY_StudentSem, student.getStudentSem());
        values.put(Student.KEY_StudentSemSection, student.getStudentSemSection());
        values.put(Student.KEY_StudentSemBatch, student.getStudentSemBatch());
        values.put(Student.KEY_StudentEmail, student.getStudentEmail());
        values.put(Student.KEY_StudentContact, student.getStudentContact());
        values.put(Student.KEY_StudentFatherName, student.getStudentFatherName());
        values.put(Student.KEY_StudentFatherContact, student.getStudentFatherContact());
        values.put(Student.KEY_StudentFatherEmail, student.getStudentFatherEmail());
        values.put(Student.KEY_StudentFatherOccupation, student.getStudentFatherOccupation());
        values.put(Student.KEY_StudentFatherIncome, student.getStudentFatherIncome());
        values.put(Student.KEY_StudentMotherName, student.getStudentMotherName());
        values.put(Student.KEY_StudentMotherContact, student.getStudentMotherContact());
        values.put(Student.KEY_StudentMotherEmail, student.getStudentMotherEmail());
        values.put(Student.KEY_StudentMotherOccupation, student.getStudentMotherOccupation());
        values.put(Student.KEY_StudentMotherIncome, student.getStudentMotherIncome());
        values.put(Student.KEY_StudentLocalGuardianName, student.getStudentLocalGuardianName());
        values.put(Student.KEY_StudentLocalGuardianContact, student.getStudentLocalGuardianContact());
        values.put(Student.KEY_StudentLocalGuardianEmail, student.getStudentLocalGuardianEmail());
        values.put(Student.KEY_StudentResidentType, student.getStudentResidentType());
        values.put(Student.KEY_StudentLocalAddress, student.getStudentLocalAddress());
        values.put(Student.KEY_StudentPermanentAddress, student.getStudentPermanentAddress());
        values.put(Student.KEY_StudentCity, student.getStudentCity());
        values.put(Student.KEY_StudentState, student.getStudentState());
        values.put(Student.KEY_StudentCountry, student.getStudentCountry());
        values.put(Student.KEY_StudentPinCode, student.getStudentPinCode());

        // Inserting Row
        studentId = (int) db.insert(Student.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return studentId;
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Student.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
