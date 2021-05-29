package com.example.iot_smartminigarden.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitResponse {
    private Retrofit mRetrofit;
    private static final RetrofitResponse outInstance = new RetrofitResponse();
    private RetrofitResponse(){
        String url ="http://127.0.0.1:8080/";
        //String url ="http://192.168.43.38:3000/";
        String url2 ="http://192.168.1.160:3000/";
        String url3 = "http://192.168.43.141:3000/";
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

