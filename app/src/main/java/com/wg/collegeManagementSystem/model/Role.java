package com.wg.collegeManagementSystem.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 17-06-2017.
 */

public class Role {
    private static final String TAG = Role.class.getSimpleName();

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
}
