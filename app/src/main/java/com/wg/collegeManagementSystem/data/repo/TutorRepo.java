package com.wg.collegeManagementSystem.data.repo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.wg.collegeManagementSystem.data.model.Tutor;
import com.wg.collegeManagementSystem.helper.DatabaseManager;

/**
 * Created by Jerry on 11-06-2017.
 */

public class TutorRepo {
    private Tutor tutor;

    public TutorRepo() {
        tutor = new Tutor();
    }

    public static String createTable() {
        return "CREATE TABLE " + Tutor.TABLE + "("
                + Tutor.KEY_TutorId + " INT  PRIMARY KEY AUTOINCREMENT,"
                + Tutor.KEY_TutorFacultyId + " INT,"
                + Tutor.KEY_TutorBatch + " VARCHAR,"
                + Tutor.KEY_TutorSection + " VARCHAR)";
    }

    public int insert(Tutor tutor) {
        int tutorId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Tutor.KEY_TutorFacultyId, tutor.getTutorFacultyId());
        values.put(Tutor.KEY_TutorBatch, tutor.getTutorBatch());
        values.put(Tutor.KEY_TutorSection, tutor.getTutorSection());

        // Inserting Row
        tutorId = (int) db.insert(Tutor.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return tutorId;
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Tutor.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
