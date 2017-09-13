package com.wg.collegeManagementSystem.data.repo;

import android.util.Log;

import com.wg.collegeManagementSystem.app.helper.UrlRequest;
import com.wg.collegeManagementSystem.data.model.FacultyMemberAllotClass;
import com.wg.collegeManagementSystem.model.FacultyMemberAllotClassList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jerry on 13-09-2017.
 */

public class FacultyMemberAllotClassRepo {
    private final String TAG = FacultyMemberAllotClassRepo.class.getSimpleName().toString();
    private FacultyMemberAllotClass facultyMemberAllotClass;

    public FacultyMemberAllotClassRepo() {

    }

    public List<FacultyMemberAllotClassList> getFacultyMemberAllotClass(String response) {
        FacultyMemberAllotClassList facultyMemberAllotClassList;
        List<FacultyMemberAllotClassList> facultyMemberAllotClassLists = new ArrayList<FacultyMemberAllotClassList>();

        try {
            JSONObject userResponse = (new JSONObject(response));
            JSONArray userArray = userResponse.getJSONArray("facultyMembersClassSubjects");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject user = userArray.getJSONObject(i);

                facultyMemberAllotClassList = new FacultyMemberAllotClassList();
                facultyMemberAllotClassList.setFmsId(user.getInt("fmsId"));
                facultyMemberAllotClassList.setFacultyMemberName(user.getString("facultyMemberName"));
                facultyMemberAllotClassList.setSubjectName(user.getString("subjectName"));
                facultyMemberAllotClassList.setSubjectAbbr(user.getString("subjectAbbr"));
                facultyMemberAllotClassList.setSubjectCode(user.getString("subjectCode"));
                facultyMemberAllotClassList.setFmsClass(user.getString("fmsClass"));
                facultyMemberAllotClassList.setFmsSection(user.getString("fmsSection"));
                facultyMemberAllotClassList.setFmsBatch(user.getString("fmsBatch"));

                facultyMemberAllotClassLists.add(facultyMemberAllotClassList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return facultyMemberAllotClassLists;
    }

    public String insert(FacultyMemberAllotClass facultyMemberAllotClass, String url) {
        String response = null;

        int fmsFacultyId = facultyMemberAllotClass.getFmsFacultyId();
        int fmsSubjectId = facultyMemberAllotClass.getFmsSubjectId();
        String fmsClass = facultyMemberAllotClass.getFmsClass();
        String fmsSection = facultyMemberAllotClass.getFmsSection();
        String fmsBatch = facultyMemberAllotClass.getFmsBatch();

        HashMap<String, String> params = new HashMap<>();
        params.put("fmsFacultyId", String.valueOf(fmsFacultyId));
        params.put("fmsSubjectId", String.valueOf(fmsSubjectId));
        params.put("fmsClass", fmsClass);
        params.put("fmsSection", fmsSection);
        params.put("fmsBatch", fmsBatch);

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.postUrlData(url, params);
        Log.d(TAG, response);
        return response;
    }

    public String update(FacultyMemberAllotClass facultyMemberAllotClass, String url, int updateFacultyNameStatus, int updateSubjectNameStatus) {
        /**
         * 1. "0" For others
         * 2. "1" For Changing faculty also
         */

        String response = null;

        int fmsId = facultyMemberAllotClass.getFmsId();
        int fmsFacultyId = facultyMemberAllotClass.getFmsFacultyId();
        int fmsSubjectId = facultyMemberAllotClass.getFmsSubjectId();
        String fmsClass = facultyMemberAllotClass.getFmsClass();
        String fmsSection = facultyMemberAllotClass.getFmsSection();
        String fmsBatch = facultyMemberAllotClass.getFmsBatch();

        HashMap<String, String> params = new HashMap<>();
        params.put("fmsClass", fmsClass);
        params.put("fmsSection", fmsSection);
        params.put("fmsBatch", fmsBatch);

        if (updateFacultyNameStatus == 1)
            params.put("fmsFacultyId", String.valueOf(fmsFacultyId));
        else
            params.put("fmsFacultyId", String.valueOf(0));
        if (updateSubjectNameStatus == 1)
            params.put("fmsSubjectId", String.valueOf(fmsSubjectId));
        else
            params.put("fmsFacultyId", String.valueOf(0));

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.putUrlData(url, fmsId, params);
        Log.d(TAG, response);
        return response;
    }

    public String delete(FacultyMemberAllotClass facultyMemberAllotClass, String url) {
        String response = null;

        int fmsId = facultyMemberAllotClass.getFmsId();

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.deleteUrlData(url, fmsId);
        Log.d(TAG, response);
        return response;
    }
}
