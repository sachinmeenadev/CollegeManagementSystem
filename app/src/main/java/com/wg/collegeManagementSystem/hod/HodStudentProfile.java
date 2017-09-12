package com.wg.collegeManagementSystem.hod;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wg.collegeManagementSystem.R;
import com.wg.collegeManagementSystem.app.config.AppURL;
import com.wg.collegeManagementSystem.app.helper.UrlRequest;

/**
 * Created by Jerry on 12-09-2017.
 */

public class HodStudentProfile extends Fragment {
    public static final String TAG = HodStudentProfile.class.getSimpleName();
    private String URL = AppURL.HOD_STUDENT;
    private String studentId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hod_fragment_student_profile, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        studentId = getArguments().getString("studentId");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Student Profile");
    }

    /**
     * For sending request to web server and get data
     */
    public String sendRequest() {
        String url = URL + "/" + studentId;

        String response = null;
        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.getUrlData(url);

        Log.e("StudentInfo", response);
        return response;
    }
}
