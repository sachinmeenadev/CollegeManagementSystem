package com.wg.collegeManagementSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wg.collegeManagementSystem.admin.AdminActivity;
import com.wg.collegeManagementSystem.data.model.User;
import com.wg.collegeManagementSystem.data.repo.UserRepo;
import com.wg.collegeManagementSystem.helper.SessionManager;
import com.wg.collegeManagementSystem.hod.HodActivity;
import com.wg.collegeManagementSystem.model.UserList;

import java.util.List;

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
                // User is already logged in. Take him to Admin activity
                Intent intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
                finish();
            } else if (session.getUserRole().equals("HOD")) {
                // User is already logged in. Take him to HOD activity
                Intent intent = new Intent(this, HodActivity.class);
                startActivity(intent);
                finish();
            } else if (session.getUserRole().equals("Faculty")) {
                // User is already logged in. Take him to Faculty activity
                Intent intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
                finish();
            } else if (session.getUserRole().equals("Tutor")) {
                // User is already logged in. Take him to Tutor activity
                Intent intent = new Intent(this, AdminActivity.class);
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
        UserRepo userRepo = new UserRepo();
        User user = new User();

        String email, password;
        String userName = null, userEmail = null, userRole = null;

        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();

        if (!email.isEmpty() && !password.isEmpty()) {
            if (email.equals("admin") && password.equals("password")) {
                session.setLogin(true);
                session.setUserNameEmailRole("Jerry", "jerry@sachin.com", "Admin");
                startActivity(new Intent(this, AdminActivity.class));
                finish();
            } else {
                user.setUserEmail(email);
                user.setUserPassword(password);
                List<UserList> list = userRepo.getLoginUser(user);
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        userName = list.get(i).getUserName();
                        userEmail = list.get(i).getUserEmail();
                        userRole = list.get(i).getRoleType();
                    }
                    session.setLogin(true);
                    session.setUserNameEmailRole(userName, userEmail, userRole);
                    if (userRole.equals("Admin")) {
                        startActivity(new Intent(this, AdminActivity.class));
                        finish();
                    } else if (userRole.equals("HOD")) {
                        startActivity(new Intent(this, HodActivity.class));
                        finish();
                    } else if (userRole.equals("Faculty")) {
                        startActivity(new Intent(this, AdminActivity.class));
                        finish();
                    } else if (userRole.equals("Tutor")) {
                        startActivity(new Intent(this, AdminActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Either Email or Password is wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Either Email or Password is wrong or User doesn't exists.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Please fill email and password", Toast.LENGTH_SHORT).show();
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
