package com.wg.collegeManagementSystem.data.repo;

import android.util.Log;

import com.wg.collegeManagementSystem.app.helper.UrlRequest;
import com.wg.collegeManagementSystem.data.model.Role;
import com.wg.collegeManagementSystem.model.RoleList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jerry on 17-06-2017.
 */

public class RoleRepo {
    private static final String TAG = RoleRepo.class.getSimpleName();
    private Role role;

    public RoleRepo() {

    }

    public List<RoleList> getRole(String response) {
        RoleList roleList;
        List<RoleList> roleLists = new ArrayList<RoleList>();

        try {
            JSONObject roleResponse = (new JSONObject(response));
            JSONArray roleArray = roleResponse.getJSONArray("roles");
            for (int i = 0; i < roleArray.length(); i++) {
                JSONObject role = roleArray.getJSONObject(i);

                roleList = new RoleList();
                roleList.setRoleId(role.getInt("roleId"));
                roleList.setRoleType(role.getString("roleType"));

                roleLists.add(roleList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return roleLists;
    }

    public String insert(Role role, String url) {
        String response = null;

        String roleType = role.getRoleType();

        HashMap<String, String> params = new HashMap<>();
        params.put("roleType", roleType);

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.postUrlData(url, params);
        Log.d(TAG, response);
        return response;
    }

    public String update(Role role, String url) {
        String response = null;

        int roleId = role.getRoleId();
        String roleType = role.getRoleType();

        HashMap<String, String> params = new HashMap<>();
        params.put("roleType", roleType);

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.putUrlData(url, roleId, params);
        Log.d(TAG, response);
        return response;
    }

    public String delete(Role role, String url) {
        String response = null;

        int roleId = role.getRoleId();

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.deleteUrlData(url, roleId);
        Log.d(TAG, response);
        return response;
    }
}
