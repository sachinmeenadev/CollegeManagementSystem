package com.wg.collegeManagementSystem.data.repo;

import com.wg.collegeManagementSystem.data.model.User;
import com.wg.collegeManagementSystem.model.UserList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 19-06-2017.
 */

public class UserRepo {
    private static final String TAG = UserRepo.class.getSimpleName();
    private User user;

    public UserRepo() {

    }

    public List<UserList> getUser(String response) {
        UserList userList;
        List<UserList> userLists = new ArrayList<UserList>();

        try {
            JSONObject userResponse = (new JSONObject(response));
            JSONArray userArray = userResponse.getJSONArray("users");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject user = userArray.getJSONObject(i);

                userList = new UserList();
                userList.setUserId(user.getInt("userId"));
                userList.setUserName(user.getString("userName"));
                userList.setUserEmail(user.getString("userEmail"));
                userList.setRoleType(user.getString("roleType"));

                userLists.add(userList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userLists;
    }
}
