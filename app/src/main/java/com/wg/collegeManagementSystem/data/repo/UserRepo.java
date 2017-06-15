package com.wg.collegeManagementSystem.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wg.collegeManagementSystem.data.model.Role;
import com.wg.collegeManagementSystem.data.model.User;
import com.wg.collegeManagementSystem.helper.DatabaseManager;
import com.wg.collegeManagementSystem.model.UserList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 11-06-2017.
 */

public class UserRepo {
    private final String TAG = UserRepo.class.getSimpleName().toString();
    private User user;

    public UserRepo() {

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
        values.put(User.KEY_UserEmail, user.getUserEmail());
        values.put(User.KEY_UserPassword, user.getUserPassword());
        values.put(User.KEY_UserRoleId, user.getUserRoleId());

        // Inserting Row
        userId = (int) db.insert(User.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return userId;
    }

    public void update(User user, int updateUserRoleStatus, int updateUserPasswordStatus) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(User.KEY_UserName, user.getNewUserName());
        values.put(User.KEY_UserEmail, user.getNewUserEmail());

        if (updateUserPasswordStatus == 1) {
            values.put(User.KEY_UserPassword, user.getUserPassword());
        }
        if (updateUserRoleStatus == 1) {
            values.put(User.KEY_UserRoleId, user.getUserRoleId());
        }

        db.update(User.TABLE, values, User.KEY_UserName + " = ? AND " + User.KEY_UserEmail + " = ?", new String[]{user.getOldUserName(), user.getOldUserEmail()});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(User user) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(User.TABLE, User.KEY_UserName + " = ? AND " + User.KEY_UserEmail + " = ?", new String[]{user.getUserName(), user.getUserEmail()});
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<UserList> getUser() {
        UserList userList;
        List<UserList> userLists = new ArrayList<UserList>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT " + User.KEY_UserId
                + ", User." + User.KEY_UserName
                + ", User." + User.KEY_UserEmail
                + ", User." + User.KEY_UserRoleId
                + ", User." + User.KEY_UserRoleId
                + ", Role." + Role.KEY_RoleType
                + " FROM " + User.TABLE + "  As User "
                + " INNER JOIN " + Role.TABLE + " Role ON Role." + Role.KEY_RoleId + " =  User." + User.KEY_UserRoleId;


        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                userList = new UserList();
                userList.setUserName(cursor.getString(cursor.getColumnIndex(User.KEY_UserName)));
                userList.setUserEmail(cursor.getString(cursor.getColumnIndex(User.KEY_UserEmail)));
                userList.setRoleType(cursor.getString(cursor.getColumnIndex(Role.KEY_RoleType)));

                userLists.add(userList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return userLists;
    }

    public List<UserList> getLoginUser(User user) {
        UserList userList;
        List<UserList> userLists = new ArrayList<UserList>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT " + User.KEY_UserId
                + ", User." + User.KEY_UserName
                + ", User." + User.KEY_UserEmail
                + ", User." + User.KEY_UserRoleId
                + ", User." + User.KEY_UserRoleId
                + ", Role." + Role.KEY_RoleType
                + " FROM " + User.TABLE + "  As User "
                + " INNER JOIN " + Role.TABLE + " Role ON Role." + Role.KEY_RoleId + " =  User." + User.KEY_UserRoleId
                + " WHERE User." + User.KEY_UserEmail + " =?"
                + " AND User." + User.KEY_UserPassword + " =?";


        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user.getUserEmail(), user.getUserPassword()});

        if (cursor.moveToFirst()) {
            do {
                userList = new UserList();
                userList.setUserName(cursor.getString(cursor.getColumnIndex(User.KEY_UserName)));
                userList.setUserEmail(cursor.getString(cursor.getColumnIndex(User.KEY_UserEmail)));
                userList.setRoleType(cursor.getString(cursor.getColumnIndex(Role.KEY_RoleType)));

                userLists.add(userList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return userLists;
    }
}
