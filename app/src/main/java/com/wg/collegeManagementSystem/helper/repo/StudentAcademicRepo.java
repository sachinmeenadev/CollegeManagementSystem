package com.wg.collegeManagementSystem.helper.repo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.wg.collegeManagementSystem.helper.DatabaseManager;
import com.wg.collegeManagementSystem.helper.model.StudentAcademic;

/**
 * Created by Jerry on 11-06-2017.
 */

public class StudentAcademicRepo {
    private StudentAcademic studentAcademic;

    public StudentAcademicRepo() {
        studentAcademic = new StudentAcademic();
    }

    public static String createTable() {
        return "CREATE TABLE " + StudentAcademic.TABLE + "("
                + StudentAcademic.KEY_StudentAcademicId + " INT  PRIMARY KEY AUTOINCREMENT,"
                + StudentAcademic.KEY_StudentAcademicStudentId + " INT,"
                + StudentAcademic.KEY_StudentAcademicSecPercentage + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicSecBoard + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicSecMedium + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicSecSchoolName + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicSrSecPercentage + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicSrSecBoard + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicSrSecMedium + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicSrSecSchoolName + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicDiplomaPercentage + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicDiplomaBoard + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicDiplomaMedium + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicDiplomaInstituteName + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicCollegeAggre + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicCollegeBackCount + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicCollegeBackSubject + " VARCHAR,"
                + StudentAcademic.KEY_StudentAcademicHobbies + " VARCHAR)";
    }

    public int insert(StudentAcademic studentAcademic) {
        int studentId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(StudentAcademic.KEY_StudentAcademicStudentId, studentAcademic.getStudentAcademicStudentId());
        values.put(StudentAcademic.KEY_StudentAcademicSecPercentage, studentAcademic.getStudentAcademicSecPercentage());
        values.put(StudentAcademic.KEY_StudentAcademicSecBoard, studentAcademic.getStudentAcademicSecBoard());
        values.put(StudentAcademic.KEY_StudentAcademicSecMedium, studentAcademic.getStudentAcademicSecMedium());
        values.put(StudentAcademic.KEY_StudentAcademicSecSchoolName, studentAcademic.getStudentAcademicSecSchoolName());
        values.put(StudentAcademic.KEY_StudentAcademicSrSecPercentage, studentAcademic.getStudentAcademicSrSecPercentage());
        values.put(StudentAcademic.KEY_StudentAcademicSrSecBoard, studentAcademic.getStudentAcademicSrSecBoard());
        values.put(StudentAcademic.KEY_StudentAcademicSrSecMedium, studentAcademic.getStudentAcademicSrSecMedium());
        values.put(StudentAcademic.KEY_StudentAcademicSrSecSchoolName, studentAcademic.getStudentAcademicSrSecSchoolName());
        values.put(StudentAcademic.KEY_StudentAcademicDiplomaPercentage, studentAcademic.getStudentAcademicDiplomaPercentage());
        values.put(StudentAcademic.KEY_StudentAcademicDiplomaBoard, studentAcademic.getStudentAcademicDiplomaBoard());
        values.put(StudentAcademic.KEY_StudentAcademicDiplomaMedium, studentAcademic.getStudentAcademicDiplomaMedium());
        values.put(StudentAcademic.KEY_StudentAcademicDiplomaInstituteName, studentAcademic.getStudentAcademicDiplomaInstituteName());
        values.put(StudentAcademic.KEY_StudentAcademicCollegeAggre, studentAcademic.getStudentAcademicCollegeAggre());
        values.put(StudentAcademic.KEY_StudentAcademicCollegeBackCount, studentAcademic.getStudentAcademicCollegeBackCount());
        values.put(StudentAcademic.KEY_StudentAcademicCollegeBackSubject, studentAcademic.getStudentAcademicCollegeBackSubject());
        values.put(StudentAcademic.KEY_StudentAcademicHobbies, studentAcademic.getStudentAcademicHobbies());
        // Inserting Row
        studentId = (int) db.insert(StudentAcademic.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return studentId;
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(StudentAcademic.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
