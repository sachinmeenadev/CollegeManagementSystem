package com.wg.collegeManagementSystem.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Created by Jerry on 09-06-2017.
 */

public class SessionManager {
    // Shared preferences file name
    private static final String PREF_NAME = "CollegeManagementSystem";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static String USER_NAME = null;
    private static String USER_ROLE = null;
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();
    // Shared Preferences
    SharedPreferences pref;
    Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUserName() {
        return pref.getString(USER_NAME, "userName");
    }

    public void setUserName(String userName) {
        editor.putString(USER_NAME, userName);
        editor.commit();
        Log.d(TAG, "User name modified!");
    }

    public String getUserRole() {
        return pref.getString(USER_ROLE, "userRole");
    }

    public void setUserRole(String userRole) {
        editor.putString(USER_ROLE, userRole);
        editor.commit();
        Log.d(TAG, "User role modified!");
    }
}

