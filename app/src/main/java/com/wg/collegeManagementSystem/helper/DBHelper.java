package com.wg.collegeManagementSystem.helper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wg.collegeManagementSystem.app.App;
import com.wg.collegeManagementSystem.helper.model.Role;
import com.wg.collegeManagementSystem.helper.model.Subject;
import com.wg.collegeManagementSystem.helper.repo.RoleRepo;
import com.wg.collegeManagementSystem.helper.repo.SubjectRepo;

/**
 * Created by Jerry on 11-06-2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "sqliteDBMultiTbl.db";
    private static final String TAG = DBHelper.class.getSimpleName().toString();

    public DBHelper() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        db.execSQL(RoleRepo.createTable());
        db.execSQL(SubjectRepo.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        // Drop table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Role.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Subject.TABLE);
        onCreate(db);
    }

}
