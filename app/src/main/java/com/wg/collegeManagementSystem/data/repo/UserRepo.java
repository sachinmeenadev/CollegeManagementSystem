package com.wg.collegeManagementSystem.data.repo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.wg.collegeManagementSystem.data.model.User;
import com.wg.collegeManagementSystem.helper.DatabaseManager;

/**
 * Created by Jerry on 11-06-2017.
 */

public class UserRepo {
    private User user;

    public UserRepo() {
        user = new User();
    }

    public static String createTable() {
        return "CREATE TABLE " + User.TABLE + "("
                + User.KEY_UserId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + User.KEY_UserName + " VARCHAR, "
                + User.KEY_UserEmail + " VARCHAR, "
                + User.KEY_UserPassword + " VARCHAR, "
                + User.KEY_UserRoleId + " INTEGER)";
    }

    public int insert(User user) {
        int userId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_UserName, user.getUserName());
        values.put(User.KEY_UserEmail, user.getUserName());
        values.put(User.KEY_UserPassword, user.getUserName());
        values.put(User.KEY_UserRoleId, user.getUserName());

        // Inserting Row
        userId = (int) db.insert(User.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return userId;
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(User.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
