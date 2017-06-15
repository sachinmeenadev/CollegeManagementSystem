package com.wg.collegeManagementSystem.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wg.collegeManagementSystem.data.model.CollegeBranch;
import com.wg.collegeManagementSystem.data.model.FacultyMember;
import com.wg.collegeManagementSystem.helper.DatabaseManager;
import com.wg.collegeManagementSystem.model.FacultyMemberList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 11-06-2017.
 */

public class FacultyMemberRepo {
    private final String TAG = FacultyMemberRepo.class.getSimpleName().toString();
    private FacultyMember facultyMember;

    public FacultyMemberRepo() {

    }

    public static String createTable() {
        return "CREATE TABLE " + FacultyMember.TABLE + "("
                + FacultyMember.KEY_FacultyMemberId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FacultyMember.KEY_FacultyMemberName + " VARCHAR, "
                + FacultyMember.KEY_FacultyMemberBranchId + " INTEGER, "
                + FacultyMember.KEY_FacultyMemberDesignation + " VARCHAR, "
                + FacultyMember.KEY_FacultyMemberContact + " VARCHAR, "
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

    public void update(FacultyMember facultyMember, int updateFacultyBranchStatus) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(FacultyMember.KEY_FacultyMemberName, facultyMember.getNewFacultyMemberName());
        values.put(FacultyMember.KEY_FacultyMemberDesignation, facultyMember.getNewFacultyMemberDesignation());
        values.put(FacultyMember.KEY_FacultyMemberContact, facultyMember.getNewFacultyMemberContact());
        values.put(FacultyMember.KEY_FacultyMemberEmail, facultyMember.getNewFacultyMemberEmail());

        if (updateFacultyBranchStatus == 1) {
            values.put(FacultyMember.KEY_FacultyMemberBranchId, facultyMember.getFacultyMemberBranchId());
        }

        db.update(FacultyMember.TABLE, values, FacultyMember.KEY_FacultyMemberName + " = ? AND " + FacultyMember.KEY_FacultyMemberDesignation + " = ? AND " + FacultyMember.KEY_FacultyMemberEmail + " = ?", new String[]{facultyMember.getOldFacultyMemberName(), facultyMember.getOldFacultyMemberDesignation(), facultyMember.getOldFacultyMemberEmail()});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(FacultyMember facultyMember) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(FacultyMember.TABLE, FacultyMember.KEY_FacultyMemberName + " = ? AND " + FacultyMember.KEY_FacultyMemberDesignation + " = ? AND " + FacultyMember.KEY_FacultyMemberEmail + " = ?", new String[]{facultyMember.getFacultyMemberName(), facultyMember.getFacultyMemberDesignation(), facultyMember.getFacultyMemberEmail()});
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<FacultyMemberList> getFacultyMember() {
        FacultyMemberList facultyMemberList;
        List<FacultyMemberList> facultyMemberLists = new ArrayList<FacultyMemberList>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT " + FacultyMember.KEY_FacultyMemberId
                + ", FacultyMember." + FacultyMember.KEY_FacultyMemberName
                + ", FacultyMember." + FacultyMember.KEY_FacultyMemberDesignation
                + ", FacultyMember." + FacultyMember.KEY_FacultyMemberContact
                + ", FacultyMember." + FacultyMember.KEY_FacultyMemberEmail
                + ", FacultyMember." + FacultyMember.KEY_FacultyMemberBranchId
                + ", CollegeBranch." + CollegeBranch.KEY_CollegeBranchName
                + ", CollegeBranch." + CollegeBranch.KEY_CollegeBranchAbbr
                + " FROM " + FacultyMember.TABLE + "  As FacultyMember "
                + " INNER JOIN " + CollegeBranch.TABLE + " CollegeBranch ON CollegeBranch." + CollegeBranch.KEY_CollegeBranchId + " =  FacultyMember." + FacultyMember.KEY_FacultyMemberBranchId;


        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                facultyMemberList = new FacultyMemberList();
                facultyMemberList.setFacultyMemberName(cursor.getString(cursor.getColumnIndex(FacultyMember.KEY_FacultyMemberName)));
                facultyMemberList.setFacultyMemberDesignation(cursor.getString(cursor.getColumnIndex(FacultyMember.KEY_FacultyMemberDesignation)));
                facultyMemberList.setFacultyMemberContact(cursor.getString(cursor.getColumnIndex(FacultyMember.KEY_FacultyMemberContact)));
                facultyMemberList.setFacultyMemberEmail(cursor.getString(cursor.getColumnIndex(FacultyMember.KEY_FacultyMemberEmail)));
                facultyMemberList.setCollegeBranchName(cursor.getString(cursor.getColumnIndex(CollegeBranch.KEY_CollegeBranchName)));
                facultyMemberList.setCollegeBranchAbbr(cursor.getString(cursor.getColumnIndex(CollegeBranch.KEY_CollegeBranchAbbr)));

                facultyMemberLists.add(facultyMemberList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return facultyMemberLists;
    }
}
