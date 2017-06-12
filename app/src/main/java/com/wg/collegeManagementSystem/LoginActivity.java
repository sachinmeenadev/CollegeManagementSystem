package com.wg.collegeManagementSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.wg.collegeManagementSystem.admin.AdminWelcomeActivity;
import com.wg.collegeManagementSystem.helper.SessionManager;

public class LoginActivity extends AppCompatActivity {
    public SessionManager session;
    private EditText inputEmail, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            if (session.getUserRole().equals("Admin")) {
                // User is already logged in. Take him to main activity
                Intent intent = new Intent(this, AdminWelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
        inputEmail = (EditText) findViewById(R.id.activity_login_input_email);
        inputPassword = (EditText) findViewById(R.id.activity_login_input_password);
    }

    /**
     * On login button click
     */
    public void activity_login_btn_login(View v) {
        String email, password;
        String aEmail = "admin", aPassword = "password";
        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        if (!email.isEmpty() && !password.isEmpty()) {
            if (email.equals(aEmail) && password.equals(aPassword)) {
                session.setLogin(true);
                session.setUserNameAndRole("Jerry", "Admin");
                startActivity(new Intent(this, AdminWelcomeActivity.class));
                finish();
            }
        } else {
            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }

    /**
     * On forgot password button click
     */
    public void activity_login_btn_forgot_password(View v) {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    /**
     * On image click button
     */
    public void activity_login_register(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
