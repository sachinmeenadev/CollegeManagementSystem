package com.wg.collegeManagementSystem.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Jerry on 18-06-2017.
 */

public class UrlRequest {

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public String getUrlData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String postUrlData(String url, HashMap<String, String> params) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String putUrlData(String url, int id, HashMap<String, String> params) throws IOException {

        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url + "/" + id)
                .addHeader("Accept", "application/x-www-form-urlencoded; q=0.5")
                .put(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String deleteUrlData(String url, int id) throws IOException {

        Request request = new Request.Builder()
                .url(url + "/" + id)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
