package com.wg.collegeManagementSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * On login button click
     */
    public void activity_login_btn_login(View v) {
        startActivity(new Intent(this, WelcomeActivity.class));
    }

    /**
     * On forgot password button click
     */
    public void activity_login_btn_forgot_password(View v) {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }
}
