package com.wg.collegeManagementSystem.data.repo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.wg.collegeManagementSystem.data.model.CollegeBranch;
import com.wg.collegeManagementSystem.helper.DatabaseManager;

/**
 * Created by Jerry on 11-06-2017.
 */

public class CollegeBranchRepo {
    private CollegeBranch collegeBranch;

    public CollegeBranchRepo() {
        collegeBranch = new CollegeBranch();
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

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(CollegeBranch.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}

