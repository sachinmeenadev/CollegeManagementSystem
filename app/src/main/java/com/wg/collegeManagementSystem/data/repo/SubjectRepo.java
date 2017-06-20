package com.wg.collegeManagementSystem.data.repo;

import android.util.Log;

import com.wg.collegeManagementSystem.app.helper.UrlRequest;
import com.wg.collegeManagementSystem.data.model.Subject;
import com.wg.collegeManagementSystem.model.SubjectList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jerry on 20-06-2017.
 */

public class SubjectRepo {
    private final String TAG = SubjectRepo.class.getSimpleName().toString();
    private Subject subject;

    public SubjectRepo() {

    }

    public List<SubjectList> getSubject(String response) {
        SubjectList subjectList;
        List<SubjectList> subjectLists = new ArrayList<SubjectList>();

        try {
            JSONObject userResponse = (new JSONObject(response));
            JSONArray userArray = userResponse.getJSONArray("subjects");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject user = userArray.getJSONObject(i);

                subjectList = new SubjectList();
                subjectList.setSubjectId(user.getInt("subjectId"));
                subjectList.setSubjectName(user.getString("subjectName"));
                subjectList.setSubjectAbbr(user.getString("subjectAbbr"));
                subjectList.setSubjectCode(user.getString("subjectCode"));

                subjectLists.add(subjectList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subjectLists;
    }

    public String insert(Subject subject, String url) {
        String response = null;

        String subjectName = subject.getSubjectName();
        String subjectAbbr = subject.getSubjectAbbr();
        String subjectCode = subject.getSubjectCode();

        HashMap<String, String> params = new HashMap<>();
        params.put("subjectName", subjectName);
        params.put("subjectAbbr", subjectAbbr);
        params.put("subjectCode", subjectCode);

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.postUrlData(url, params);
        Log.d(TAG, response);
        return response;
    }

    public String update(Subject subject, String url) {
        String response = null;

        int subjectId = subject.getSubjectId();
        String subjectName = subject.getSubjectName();
        String subjectAbbr = subject.getSubjectAbbr();
        String subjectCode = subject.getSubjectCode();


        HashMap<String, String> params = new HashMap<>();
        params.put("subjectName", subjectName);
        params.put("subjectAbbr", subjectAbbr);
        params.put("subjectCode", subjectCode);

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.putUrlData(url, subjectId, params);
        Log.d(TAG, response);
        return response;
    }

    public String delete(Subject subject, String url) {
        String response = null;

        int subjectId = subject.getSubjectId();

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.deleteUrlData(url, subjectId);
        Log.d(TAG, response);
        return response;
    }
}
