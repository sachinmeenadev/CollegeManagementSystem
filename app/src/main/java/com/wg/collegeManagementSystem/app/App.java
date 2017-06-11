package com.wg.collegeManagementSystem.app;

import android.app.Application;
import android.content.Context;

import com.wg.collegeManagementSystem.helper.DBHelper;
import com.wg.collegeManagementSystem.helper.DatabaseManager;

/**
 * Created by Jerry on 11-06-2017.
 */

public class App extends Application {
    private static Context context;
    private static DBHelper dbHelper;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHelper();
        DatabaseManager.initializeInstance(dbHelper);

    }

}
