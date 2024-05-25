package com.example.newsandroidproject.common;

import com.example.newsandroidproject.model.dto.ResponseException;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class JsonParser {
    public static ResponseException parseError(Response<?> response) throws IOException {
        Gson gson = new Gson();
        TypeAdapter<ResponseException> adapter = gson.getAdapter(ResponseException.class);
        ResponseBody responseBody = response.errorBody();
        if (responseBody != null) {
            return adapter.fromJson(responseBody.string());
        } else {
            return new ResponseException("Unknown error", -1);
        }
    }
}
