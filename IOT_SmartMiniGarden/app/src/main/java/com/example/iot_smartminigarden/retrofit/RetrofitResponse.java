package com.example.iot_smartminigarden.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitResponse {
    private Retrofit mRetrofit;
    private static final RetrofitResponse outInstance = new RetrofitResponse();
    private RetrofitResponse(){
        String url ="http://localhost:8081/";
        mRetrofit=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
    }
    public static RetrofitResponse getInstance(){
        return outInstance;
    }
    public Retrofit getRetrofit(){
        return mRetrofit;
    }
}

