package com.wg.collegeManagementSystem.data.repo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.wg.collegeManagementSystem.data.model.Subject;
import com.wg.collegeManagementSystem.helper.DatabaseManager;

/**
 * Created by Jerry on 11-06-2017.
 */

public class SubjectRepo {

    private Subject subject;

    public SubjectRepo() {
        subject = new Subject();
    }

    public static String createTable() {
        return "CREATE TABLE " + Subject.TABLE + "("
                + Subject.KEY_SubjectId + " INT  PRIMARY KEY AUTOINCREMENT,"
                + Subject.KEY_SubjectName + " VARCHAR,"
                + Subject.KEY_SubjectAbbr + " VARCHAR,"
                + Subject.KEY_SubjectCode + " VARCHAR)";
    }

    public int insert(Subject subject) {
        int subjectId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Subject.KEY_SubjectName, subject.getSubjectName());
        values.put(Subject.KEY_SubjectAbbr, subject.getSubjectAbbr());
        values.put(Subject.KEY_SubjectCode, subject.getSubjectCode());

        // Inserting Row
        subjectId = (int) db.insert(Subject.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return subjectId;
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Subject.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}