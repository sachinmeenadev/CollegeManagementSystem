package in.webguides.collegeManagementSystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.webguides.collegeManagementSystem.admin.AdminActivity;
import in.webguides.collegeManagementSystem.app.config.AppController;
import in.webguides.collegeManagementSystem.app.config.AppURL;
import in.webguides.collegeManagementSystem.app.helper.SessionManager;
import in.webguides.collegeManagementSystem.hod.HodActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    public SessionManager session;
    private ProgressDialog pDialog;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;

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
            }
        }
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setColorScheme(SignInButton.COLOR_AUTO);
        btnSignIn.setScopes(gso.getScopeArray());
    }

    @Override
    public void onClick(View v) {
        signIn();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personPhotoUrl = AppURL.PUBLIC_URL + "/img/user.png";
            String personName = acct.getDisplayName();
            String email = acct.getEmail();
            Uri userPhoto = acct.getPhotoUrl();
            if (userPhoto != null) {
                personPhotoUrl = userPhoto.toString();
            }
            checkLogin(personName, email, personPhotoUrl);
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this, "Unable To Sign In", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * function to verify login details in mysql db
     */

    private void checkLogin(final String personName, final String email, final String personPhotoUrl) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppURL.URL_LOGIN, new Response.Listener<String>() {

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
                        session.setUserImage(personPhotoUrl);
                        if (userRole.equals("Admin")) {
                            startActivity(new Intent(getBaseContext(), AdminActivity.class));
                            finish();
                        } else if (userRole.equals("HOD")) {
                            String BranchId = user.getString("facultyMemberCurrentBranchId");
                            session.setBranchId(BranchId);
                            startActivity(new Intent(getBaseContext(), AdminActivity.class));
                            finish();
                        }
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                            }
                        });
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                        }
                    });
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "An unresolvable error has occurred :\n" + connectionResult);
    }
}
