package com.example.newsandroidproject.retrofit;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static Retrofit retrofit;
//    private static final String BASE_URL = "http://192.168.0.101:8080/";
     private static final String BASE_URL = "http://10.0.209.105:8080/";

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new TokenInterceptor(context))
                    .addInterceptor(new AuthenticationInterception(context))  // Thêm AuthInterceptor
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
