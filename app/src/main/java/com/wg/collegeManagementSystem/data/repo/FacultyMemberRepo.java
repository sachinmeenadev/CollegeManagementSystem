package com.wg.collegeManagementSystem.data.repo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.wg.collegeManagementSystem.data.model.FacultyMember;
import com.wg.collegeManagementSystem.helper.DatabaseManager;

/**
 * Created by Jerry on 11-06-2017.
 */

public class FacultyMemberRepo {
    private FacultyMember facultyMember;

    public FacultyMemberRepo() {
        facultyMember = new FacultyMember();
    }

    public static String createTable() {
        return "CREATE TABLE " + FacultyMember.TABLE + "("
                + FacultyMember.KEY_FacultyMemberId + " INT  PRIMARY KEY AUTOINCREMENT,"
                + FacultyMember.KEY_FacultyMemberName + " VARCHAR,"
                + FacultyMember.KEY_FacultyMemberBranchId + " INT,"
                + FacultyMember.KEY_FacultyMemberDesignation + " VARCHAR,"
                + FacultyMember.KEY_FacultyMemberContact + " VARCHAR,"
                + FacultyMember.KEY_FacultyMemberEmail + " VARCHAR)";
    }

    public int insert(FacultyMember facultyMember) {
        int facultyMemberId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(FacultyMember.KEY_FacultyMemberName, facultyMember.getFacultyMemberName());
        values.put(FacultyMember.KEY_FacultyMemberBranchId, facultyMember.getFacultyMemberBranchId());
        values.put(FacultyMember.KEY_FacultyMemberDesignation, facultyMember.getFacultyMemberDesignation());
        values.put(FacultyMember.KEY_FacultyMemberContact, facultyMember.getFacultyMemberContact());
        values.put(FacultyMember.KEY_FacultyMemberEmail, facultyMember.getFacultyMemberEmail());

        // Inserting Row
        facultyMemberId = (int) db.insert(FacultyMember.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return facultyMemberId;
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(FacultyMember.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
