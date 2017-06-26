package com.wg.collegeManagementSystem.data.repo;

import android.util.Log;

import com.wg.collegeManagementSystem.app.helper.UrlRequest;
import com.wg.collegeManagementSystem.data.model.FacultyMember;
import com.wg.collegeManagementSystem.model.FacultyMemberList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jerry on 21-06-2017.
 */

public class FacultyMemberRepo {
    private final String TAG = FacultyMemberRepo.class.getSimpleName().toString();
    private FacultyMember facultyMember;

    public FacultyMemberRepo() {

    }

    public List<FacultyMemberList> getFacultyMember(String response) {
        FacultyMemberList facultyMemberList;
        List<FacultyMemberList> facultyMemberLists = new ArrayList<FacultyMemberList>();

        try {
            JSONObject userResponse = (new JSONObject(response));
            JSONArray userArray = userResponse.getJSONArray("facultyMembers");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject user = userArray.getJSONObject(i);

                facultyMemberList = new FacultyMemberList();
                facultyMemberList.setFacultyMemberId(user.getInt("facultyMemberId"));
                facultyMemberList.setFacultyMemberName(user.getString("facultyMemberName"));
                facultyMemberList.setFacultyMemberDesignation(user.getString("facultyMemberDesignation"));
                facultyMemberList.setFacultyMemberContact(user.getString("facultyMemberContact"));
                facultyMemberList.setFacultyMemberEmail(user.getString("facultyMemberEmail"));
                facultyMemberList.setCollegeBranchName(user.getString("collegeBranchName"));
                facultyMemberList.setCollegeBranchAbbr(user.getString("collegeBranchAbbr"));
                facultyMemberList.setCurrentBranchName(user.getString("currentBranchName"));
                facultyMemberList.setCurrentBranchAbbr(user.getString("currentBranchAbbr"));

                facultyMemberLists.add(facultyMemberList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return facultyMemberLists;
    }

    public String insert(FacultyMember facultyMember, String url) {
        String response = null;

        String facultyMemberName = facultyMember.getFacultyMemberName();
        String facultyMemberDesignation = facultyMember.getFacultyMemberDesignation();
        String facultyMemberContact = facultyMember.getFacultyMemberContact();
        String facultyMemberEmail = facultyMember.getFacultyMemberEmail();
        int facultyMemberBranchId = facultyMember.getFacultyMemberBranchId();
        int facultyMemberCurrentBranchId = facultyMember.getFacultyMemberCurrentBranchId();

        HashMap<String, String> params = new HashMap<>();
        params.put("facultyMemberName", facultyMemberName);
        params.put("facultyMemberBranchId", String.valueOf(facultyMemberBranchId));
        params.put("facultyMemberCurrentBranchId", String.valueOf(facultyMemberCurrentBranchId));
        params.put("facultyMemberDesignation", facultyMemberDesignation);
        params.put("facultyMemberContact", String.valueOf(facultyMemberContact));
        params.put("facultyMemberEmail", String.valueOf(facultyMemberEmail));

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.postUrlData(url, params);
        Log.d(TAG, response);
        return response;
    }

    public String update(FacultyMember facultyMember, String url, int updateFacultyBranchStatus, int updateFacultyCurrentBranchStatus) {
        /**
         * 1. "0" Without Branch
         * 2. "1" With Parent Branch
         * 3. "1" With Current Branch
         * 4. "1" With Both Branch
         */

        String response = null;

        int facultyMemberId = facultyMember.getFacultyMemberId();
        String facultyMemberName = facultyMember.getFacultyMemberName();
        String facultyMemberDesignation = facultyMember.getFacultyMemberDesignation();
        String facultyMemberContact = facultyMember.getFacultyMemberContact();
        String facultyMemberEmail = facultyMember.getFacultyMemberEmail();
        int facultyMemberBranchId = facultyMember.getFacultyMemberBranchId();
        int facultyMemberCurrentBranchId = facultyMember.getFacultyMemberCurrentBranchId();
        int facultyMemberUpdateStatus = 0;

        HashMap<String, String> params = new HashMap<>();
        params.put("facultyMemberName", facultyMemberName);
        params.put("facultyMemberBranchId", String.valueOf(facultyMemberBranchId));
        params.put("facultyMemberCurrentBranchId", String.valueOf(facultyMemberCurrentBranchId));
        params.put("facultyMemberDesignation", facultyMemberDesignation);
        params.put("facultyMemberContact", String.valueOf(facultyMemberContact));
        params.put("facultyMemberEmail", String.valueOf(facultyMemberEmail));

        if (updateFacultyBranchStatus == 1)
            facultyMemberUpdateStatus = 1;
        if (updateFacultyCurrentBranchStatus == 1)
            facultyMemberUpdateStatus = 2;
        if (updateFacultyBranchStatus == 1 && updateFacultyCurrentBranchStatus == 1)
            facultyMemberUpdateStatus = 3;

        params.put("facultyMemberUpdateStatus", String.valueOf(facultyMemberUpdateStatus));
        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.putUrlData(url, facultyMemberId, params);
        Log.d(TAG, response);
        return response;
    }

    public String delete(FacultyMember facultyMember, String url) {
        String response = null;

        int facultyMemberId = facultyMember.getFacultyMemberId();

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.deleteUrlData(url, facultyMemberId);
        Log.d(TAG, response);
        return response;
    }
}

