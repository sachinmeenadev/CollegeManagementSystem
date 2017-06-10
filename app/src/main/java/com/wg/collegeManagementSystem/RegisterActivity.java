package com.wg.collegeManagementSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    /**
     * On register button click
     */
    public void activity_register_btn_register(View v) {
        Toast.makeText(this, "Register Button Click", Toast.LENGTH_SHORT).show();
    }

    /**
     * On login button click
     */
    public void activity_register_btn_login(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
