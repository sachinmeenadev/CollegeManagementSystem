package com.wg.collegeManagementSystem.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wg.collegeManagementSystem.data.model.Subject;
import com.wg.collegeManagementSystem.helper.DatabaseManager;
import com.wg.collegeManagementSystem.model.SubjectList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 11-06-2017.
 */

public class SubjectRepo {
    private final String TAG = SubjectRepo.class.getSimpleName().toString();
    private Subject subject;

    public SubjectRepo() {

    }

    public static String createTable() {
        return "CREATE TABLE " + Subject.TABLE + "("
                + Subject.KEY_SubjectId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Subject.KEY_SubjectName + " VARCHAR, "
                + Subject.KEY_SubjectAbbr + " VARCHAR, "
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

    public void update(Subject subject) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Subject.KEY_SubjectName, subject.getNewSubjectName());
        values.put(Subject.KEY_SubjectAbbr, subject.getNewSubjectAbbr());
        values.put(Subject.KEY_SubjectCode, subject.getNewSubjectCode());

        db.update(Subject.TABLE, values, Subject.KEY_SubjectName + " = ? AND " + Subject.KEY_SubjectAbbr + " = ? AND " + Subject.KEY_SubjectCode + " = ?", new String[]{subject.getOldSubjectName(), subject.getOldSubjectAbbr(), subject.getOldSubjectCode()});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(Subject subject) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Subject.TABLE, Subject.KEY_SubjectName + " = ? AND " + Subject.KEY_SubjectAbbr + " = ? AND " + Subject.KEY_SubjectCode + " = ?", new String[]{subject.getOldSubjectName(), subject.getOldSubjectAbbr(), subject.getOldSubjectCode()});
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<SubjectList> getSubject() {
        SubjectList subjectList;
        List<SubjectList> subjectLists = new ArrayList<SubjectList>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT " + Subject.KEY_SubjectId
                + "," + Subject.KEY_SubjectName
                + "," + Subject.KEY_SubjectAbbr
                + "," + Subject.KEY_SubjectCode
                + " FROM " + Subject.TABLE;

        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                subjectList = new SubjectList();
                subjectList.setSubjectName(cursor.getString(cursor.getColumnIndex(Subject.KEY_SubjectName)));
                subjectList.setSubjectAbbr(cursor.getString(cursor.getColumnIndex(Subject.KEY_SubjectAbbr)));
                subjectList.setSubjectCode(cursor.getString(cursor.getColumnIndex(Subject.KEY_SubjectCode)));

                subjectLists.add(subjectList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return subjectLists;
    }
}