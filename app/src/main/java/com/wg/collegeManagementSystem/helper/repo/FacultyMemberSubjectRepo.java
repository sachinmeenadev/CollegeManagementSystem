package com.wg.collegeManagementSystem.helper.repo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.wg.collegeManagementSystem.helper.DatabaseManager;
import com.wg.collegeManagementSystem.helper.model.FacultyMemberSubject;

/**
 * Created by Jerry on 11-06-2017.
 */

public class FacultyMemberSubjectRepo {
    private FacultyMemberSubject facultyMemberSubject;

    public FacultyMemberSubjectRepo() {
        facultyMemberSubject = new FacultyMemberSubject();
    }

    public static String createTable() {
        return "CREATE TABLE " + FacultyMemberSubject.TABLE + "("
                + FacultyMemberSubject.KEY_FMSId + " INT  PRIMARY KEY AUTOINCREMENT,"
                + FacultyMemberSubject.KEY_FMSFacultyId + " INT,"
                + FacultyMemberSubject.KEY_FMSSubjectId + " INT)";
    }

    public int insert(FacultyMemberSubject facultyMemberSubject) {
        int fmsId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(FacultyMemberSubject.KEY_FMSFacultyId, facultyMemberSubject.getFmsFacultyId());
        values.put(FacultyMemberSubject.KEY_FMSSubjectId, facultyMemberSubject.getFmsSubjectId());

        // Inserting Row
        fmsId = (int) db.insert(FacultyMemberSubject.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return fmsId;
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(FacultyMemberSubject.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
