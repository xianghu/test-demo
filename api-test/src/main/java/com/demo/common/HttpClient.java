package com.demo.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpClient {

    private static final String token = "fc3aa251833660d75351d08a164d045c126f6696b050c4bc4ac2b901dfe71899";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static HashMap<String, Object> call(String url, String method, Map<String, Object> requestBodyMap) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();

        RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(requestBodyMap));

        Request request = new Request.Builder()
                .url(url)
                .method(method, requestBody)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            HashMap<String, Object> failedResponse = new LinkedHashMap<>();
            failedResponse.put("code", response.code());
            failedResponse.put("message", response.message());
            return failedResponse;
        }

        String responseString;
        if (response.body() != null) {
            responseString = response.body().string();
        } else {
            responseString = ""; // empty body
        }

        Gson gson = new GsonBuilder()
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create();

        return gson.fromJson(responseString, HashMap.class);
    }
}
