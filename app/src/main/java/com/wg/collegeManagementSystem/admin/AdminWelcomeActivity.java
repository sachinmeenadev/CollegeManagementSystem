package com.wg.collegeManagementSystem.admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;

import com.wg.collegeManagementSystem.R;
import com.wg.collegeManagementSystem.helper.SessionManager;

public class AdminWelcomeActivity extends AppCompatActivity {
    public SessionManager session;
    private AppCompatTextView lblWelcomeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);
        session = new SessionManager(getApplicationContext());
        lblWelcomeName = (AppCompatTextView) findViewById(R.id.admin_activity_lbl_welcome_name);
        lblWelcomeName.setText(session.getUserName());
    }
}
