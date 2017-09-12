package com.wg.collegeManagementSystem.data.repo;

import android.util.Log;

import com.wg.collegeManagementSystem.app.helper.UrlRequest;
import com.wg.collegeManagementSystem.data.model.Tutor;
import com.wg.collegeManagementSystem.model.TutorList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jerry on 09-07-2017.
 */

public class TutorRepo {
    private final String TAG = TutorRepo.class.getSimpleName().toString();
    private Tutor tutor;

    public TutorRepo() {

    }

    public List<TutorList> getTutor(String response) {
        TutorList tutorList;
        List<TutorList> tutorLists = new ArrayList<TutorList>();

        try {
            JSONObject userResponse = (new JSONObject(response));
            JSONArray userArray = userResponse.getJSONArray("tutors");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject user = userArray.getJSONObject(i);

                tutorList = new TutorList();
                tutorList.setTutorId(user.getInt("tutorId"));
                tutorList.setFacultyMemberName(user.getString("facultyMemberName"));
                tutorList.setFacultyMemberDesignation(user.getString("facultyMemberDesignation"));
                tutorList.setFacultyMemberContact(user.getString("facultyMemberContact"));
                tutorList.setFacultyMemberEmail(user.getString("facultyMemberEmail"));
                tutorList.setTutorBatch(user.getString("tutorBatch"));
                tutorList.setTutorClass(user.getString("tutorClass"));
                tutorList.setTutorSection(user.getString("tutorSection"));

                tutorLists.add(tutorList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tutorLists;
    }

    public String insert(Tutor tutor, String url) {
        String response = null;

        int tutorFacultyId = tutor.getTutorFacultyId();
        String tutorClass = tutor.getTutorClass();
        String tutorSection = tutor.getTutorSection();
        String tutorBatch = tutor.getTutorBatch();

        HashMap<String, String> params = new HashMap<>();
        params.put("tutorFacultyId", String.valueOf(tutorFacultyId));
        params.put("tutorClass", tutorClass);
        params.put("tutorSection", tutorSection);
        params.put("tutorBatch", tutorBatch);

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.postUrlData(url, params);
        Log.d(TAG, response);
        return response;
    }

    public String update(Tutor tutor, String url, int updateTutorNameStatus) {
        /**
         * 1. "0" For others
         * 2. "1" For Changing faculty also
         */

        String response = null;

        int tutorId = tutor.getTutorId();
        int tutorFacultyId = tutor.getTutorFacultyId();
        String tutorClass = tutor.getTutorClass();
        String tutorSection = tutor.getTutorSection();
        String tutorBatch = tutor.getTutorBatch();

        HashMap<String, String> params = new HashMap<>();
        params.put("tutorClass", tutorClass);
        params.put("tutorSection", tutorSection);
        params.put("tutorBatch", tutorBatch);

        if (updateTutorNameStatus == 1)
            params.put("tutorFacultyId", String.valueOf(tutorFacultyId));

        params.put("tutorNameUpdateStatus", String.valueOf(updateTutorNameStatus));
        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.putUrlData(url, tutorId, params);
        Log.d(TAG, response);
        return response;
    }

    public String delete(Tutor tutor, String url) {
        String response = null;

        int tutorId = tutor.getTutorId();

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.deleteUrlData(url, tutorId);
        Log.d(TAG, response);
        return response;
    }
}
