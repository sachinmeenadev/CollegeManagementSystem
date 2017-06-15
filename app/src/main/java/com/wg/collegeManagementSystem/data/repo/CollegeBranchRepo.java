package com.wg.collegeManagementSystem.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wg.collegeManagementSystem.data.model.CollegeBranch;
import com.wg.collegeManagementSystem.helper.DatabaseManager;
import com.wg.collegeManagementSystem.model.CollegeBranchList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 11-06-2017.
 */

public class CollegeBranchRepo {
    private final String TAG = CollegeBranchRepo.class.getSimpleName().toString();
    private CollegeBranch collegeBranch;

    public CollegeBranchRepo() {

    }

    public static String createTable() {
        return "CREATE TABLE " + CollegeBranch.TABLE + "("
                + CollegeBranch.KEY_CollegeBranchId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CollegeBranch.KEY_CollegeBranchName + " VARCHAR, "
                + CollegeBranch.KEY_CollegeBranchAbbr + " VARCHAR)";
    }

    public int insert(CollegeBranch collegeBranch) {
        int collegeBranchId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(CollegeBranch.KEY_CollegeBranchName, collegeBranch.getCollegeBranchName());
        values.put(CollegeBranch.KEY_CollegeBranchAbbr, collegeBranch.getCollegeBranchAbbr());

        // Inserting Row
        collegeBranchId = (int) db.insert(CollegeBranch.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return collegeBranchId;
    }

    public void update(CollegeBranch collegeBranch) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(CollegeBranch.KEY_CollegeBranchName, collegeBranch.getNewCollegeBranchName());
        values.put(CollegeBranch.KEY_CollegeBranchAbbr, collegeBranch.getNewCollegeBranchAbbr());

        db.update(CollegeBranch.TABLE, values, CollegeBranch.KEY_CollegeBranchName + " = ? AND " + CollegeBranch.KEY_CollegeBranchAbbr + " = ?", new String[]{collegeBranch.getOldCollegeBranchName(), collegeBranch.getOldCollegeBranchAbbr()});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(CollegeBranch collegeBranch) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(CollegeBranch.TABLE, CollegeBranch.KEY_CollegeBranchName + " = ? AND " + CollegeBranch.KEY_CollegeBranchAbbr + " = ?", new String[]{collegeBranch.getCollegeBranchName(), collegeBranch.getCollegeBranchAbbr()});
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<CollegeBranchList> getCollegeBranch() {
        CollegeBranchList collegeBranchList;
        List<CollegeBranchList> collegeBranchLists = new ArrayList<CollegeBranchList>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT " + CollegeBranch.KEY_CollegeBranchId
                + "," + CollegeBranch.KEY_CollegeBranchName
                + "," + CollegeBranch.KEY_CollegeBranchAbbr
                + " FROM " + CollegeBranch.TABLE;

        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                collegeBranchList = new CollegeBranchList();
                collegeBranchList.setCollegeBranchId(cursor.getInt(cursor.getColumnIndex(CollegeBranch.KEY_CollegeBranchId)));
                collegeBranchList.setCollegeBranchName(cursor.getString(cursor.getColumnIndex(CollegeBranch.KEY_CollegeBranchName)));
                collegeBranchList.setCollegeBranchAbbr(cursor.getString(cursor.getColumnIndex(CollegeBranch.KEY_CollegeBranchAbbr)));

                collegeBranchLists.add(collegeBranchList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return collegeBranchLists;
    }
}

