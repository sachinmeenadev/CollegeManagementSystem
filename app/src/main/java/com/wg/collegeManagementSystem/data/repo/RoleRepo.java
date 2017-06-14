package com.wg.collegeManagementSystem.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wg.collegeManagementSystem.data.model.Role;
import com.wg.collegeManagementSystem.helper.DatabaseManager;
import com.wg.collegeManagementSystem.model.RoleList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 11-06-2017.
 */

public class RoleRepo {
    private final String TAG = RoleRepo.class.getSimpleName().toString();
    private Role role;

    public RoleRepo() {

    }

    public static String createTable() {
        return "CREATE TABLE " + Role.TABLE + "("
                + Role.KEY_RoleId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
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

    public void update(Role role) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Role.KEY_RoleType, role.getNewRoleType());

        db.update(Role.TABLE, values, Role.KEY_RoleType + " = ?", new String[]{role.getOldRoleType()});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(Role role) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Role.TABLE, Role.KEY_RoleType + " = ?", new String[]{role.getRoleType()});
        DatabaseManager.getInstance().closeDatabase();
    }
    public List<RoleList> getRole() {
        RoleList roleList;
        List<RoleList> roleLists = new ArrayList<RoleList>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = " SELECT " + Role.KEY_RoleId
                + "," + Role.KEY_RoleType
                + " FROM " + Role.TABLE;

        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                roleList = new RoleList();
                roleList.setRoleId(cursor.getInt(cursor.getColumnIndex(Role.KEY_RoleId)));
                roleList.setRoleType(cursor.getString(cursor.getColumnIndex(Role.KEY_RoleType)));

                roleLists.add(roleList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return roleLists;
    }
}