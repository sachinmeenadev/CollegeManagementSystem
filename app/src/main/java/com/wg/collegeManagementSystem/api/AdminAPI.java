package com.wg.collegeManagementSystem.api;

import com.wg.collegeManagementSystem.model.Role;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by Jerry on 16-06-2017.
 */

public interface AdminAPI {
    @GET("roles/")
    Call<Role> getRoles(Callback<Role> callback);
}
