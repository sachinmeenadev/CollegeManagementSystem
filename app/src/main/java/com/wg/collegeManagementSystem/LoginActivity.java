package com.wg.collegeManagementSystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wg.collegeManagementSystem.admin.AdminActivity;
import com.wg.collegeManagementSystem.app.AppConfig;
import com.wg.collegeManagementSystem.app.AppController;
import com.wg.collegeManagementSystem.app.SessionManager;
import com.wg.collegeManagementSystem.hod.HodActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    public SessionManager session;
    private EditText inputEmail, inputPassword;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

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

        String email, password;
        String userName = null, userEmail = null, userRole = null;

        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();

        if (!email.isEmpty() && !password.isEmpty()) {
            checkLogin(email, password);
        } else {
            Toast.makeText(this, "Please fill email and password", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * function to verify login details in mysql db
     */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        session.setLogin(true);
                        String userUniqueId = jObj.getString("userUniqueId");

                        JSONObject user = jObj.getJSONObject("user");
                        String userName = user.getString("userName");
                        String userEmail = user.getString("userEmail");
                        String userRole = user.getString("userRole");

                        session.setLogin(true);
                        session.setUserUniqueId(userUniqueId);
                        session.setUserName(userName);
                        session.setUserEmail(userEmail);
                        session.setUserRole(userRole);
                        if (userRole.equals("Admin")) {
                            startActivity(new Intent(getBaseContext(), AdminActivity.class));
                            finish();
                        }
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("userEmail", email);
                params.put("userPassword", password);

                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
