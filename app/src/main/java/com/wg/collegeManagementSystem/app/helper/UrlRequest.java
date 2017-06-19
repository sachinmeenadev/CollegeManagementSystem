package com.wg.collegeManagementSystem.app.helper;

import java.io.IOException;
import java.net.SocketTimeoutException;
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

    public String getUrlData(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            int responseCode = response.code();
            if (responseCode >= 200 && responseCode < 300) {
                return response.body().string();
            } else {
                return "ERROR : " + response.code() + "\nMESSAGE : " + response.message() + "\nNETWORK RESPONSE : " + response.networkResponse();
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "ERROR : Connection Time Out. Failed To Connect";
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR : On Connection With Server";
        }
    }

    public String postUrlData(String url, HashMap<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();

            int responseCode = response.code();
            if (responseCode >= 200 && responseCode < 300) {
                return response.body().string();
            } else {
                return "ERROR : " + response.code() + "\nMESSAGE : " + response.message() + "\nNETWORK RESPONSE : " + response.networkResponse();
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "ERROR : Connection Time Out. Failed To Connect";
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR : On Connection With Server";
        }
    }

    public String putUrlData(String url, int id, HashMap<String, String> params) {

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
        try {
            Response response = client.newCall(request).execute();
            int responseCode = response.code();
            if (responseCode >= 200 && responseCode < 300) {
                return response.body().string();
            } else {
                return "ERROR : " + response.code() + "\nMESSAGE : " + response.message() + "\nNETWORK RESPONSE : " + response.networkResponse();
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "ERROR : Connection Time Out. Failed To Connect";
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR : On Connection With Server";
        }
    }

    public String deleteUrlData(String url, int id) {

        Request request = new Request.Builder()
                .url(url + "/" + id)
                .delete()
                .build();
        try {
            Response response = client.newCall(request).execute();
            int responseCode = response.code();
            if (responseCode >= 200 && responseCode < 300) {
                return response.body().string();
            } else {
                return "ERROR : " + response.code() + "\nMESSAGE : " + response.message() + "\nNETWORK RESPONSE : " + response.networkResponse();
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "ERROR : Connection Time Out. Failed To Connect";
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR : On Connection With Server";
        }
    }
}
