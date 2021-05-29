package com.example.iot_smartminigarden.retrofit

import com.example.iot_smartminigarden.model.Led
import com.example.iot_smartminigarden.model.Sprinkler
import com.example.iot_smartminigarden.model.WeatherInfo
import java.util.List;
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface InterfaceNetwork {
    @GET("getListBulb1")
    fun getLeds(): Call<List<Led>>

    @GET("getListBulb2")
    fun getSprinklers(): Call<List<Sprinkler>>

    @GET("turnOnOffBulb")
    fun turnLed(@Query("id") id: Int, @Query("value") value: Int): Call<Void>

    @GET("getWeatherInfo")
    fun getWeatherInfo(): Call<List<WeatherInfo>>

    @GET("login")
    fun login(@Query("username") username: String, @Query("password") password: String): Call<Boolean>
}
