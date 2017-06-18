package com.wg.collegeManagementSystem.app;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jerry on 18-06-2017.
 */

public class UrlRequest {
    OkHttpClient client = new OkHttpClient();

    public String getUrlData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
