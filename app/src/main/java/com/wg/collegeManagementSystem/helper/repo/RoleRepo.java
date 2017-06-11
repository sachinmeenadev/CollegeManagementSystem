package com.wg.collegeManagementSystem.helper.repo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.wg.collegeManagementSystem.helper.DatabaseManager;
import com.wg.collegeManagementSystem.helper.model.Role;

/**
 * Created by Jerry on 11-06-2017.
 */

public class RoleRepo {

    private Role role;

    public RoleRepo() {
        role = new Role();
    }

    public static String createTable() {
        return "CREATE TABLE " + Role.TABLE + "("
                + Role.KEY_RoleId + " INT  PRIMARY KEY AUTOINCREMENT,"
                + Role.KEY_RoleType + " VARCHAR)";
    }

    public int insert(Role role) {
        int roleId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Role.KEY_RoleType, role.getRoleType());

        // Inserting Row
        roleId = (int) db.insert(Role.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return roleId;
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Role.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}