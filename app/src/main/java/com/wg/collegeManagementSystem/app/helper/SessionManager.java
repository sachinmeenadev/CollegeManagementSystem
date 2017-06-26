package com.wg.collegeManagementSystem.app.helper;

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
    private static String USER_NAME = "USER_NAME";
    private static String USER_EMAIL = "USER_EMAIL";
    private static String USER_ROLE = "USER_ROLE";
    private static String USER_UNIQUE_ID = "USER_UNIQUE_ID";
    private static String USER_IMAGE = "USER_IMAGE";
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
    }

    public void setLogin(boolean isLoggedIn) {
        editor = pref.edit();
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

    public String getUserEmail() {
        return pref.getString(USER_EMAIL, "userEmail");
    }

    public void setUserEmail(String userEmail) {
        editor.putString(USER_EMAIL, userEmail);
        editor.commit();
        Log.d(TAG, "User email modified!");
    }

    public String getUserRole() {
        return pref.getString(USER_ROLE, "userRole");
    }

    public void setUserRole(String userRole) {
        editor.putString(USER_ROLE, userRole);
        editor.commit();
        Log.d(TAG, "User role modified!");
    }

    public String getUserUniqueId() {
        return pref.getString(USER_UNIQUE_ID, "userUniqueId");
    }

    public void setUserUniqueId(String userUniqueId) {
        editor.putString(USER_UNIQUE_ID, userUniqueId);
        editor.commit();
        Log.d(TAG, "User unique id modified!");
    }

    public String getUserImage() {
        return pref.getString(USER_IMAGE, "userImage");
    }

    public void setUserImage(String userImage) {
        editor.putString(USER_IMAGE, userImage);
        editor.commit();
        Log.d(TAG, "User image modified!");
    }
}

