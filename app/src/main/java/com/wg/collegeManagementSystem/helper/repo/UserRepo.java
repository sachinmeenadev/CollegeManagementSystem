package com.wg.collegeManagementSystem.helper.repo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.wg.collegeManagementSystem.helper.DatabaseManager;
import com.wg.collegeManagementSystem.helper.model.User;

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
                + User.KEY_UserId + " INT  PRIMARY KEY AUTOINCREMENT,"
                + User.KEY_UserName + " VARCHAR,"
                + User.KEY_UserEmail + " VARCHAR,"
                + User.KEY_UserPassword + " VARCHAR,"
                + User.KEY_UserRoleId + " INT)";
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
