package com.example.iot_smartminigarden.retrofit

import com.example.iot_smartminigarden.model.Led
import com.example.iot_smartminigarden.model.Sprinkler
import com.example.iot_smartminigarden.model.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface InterfaceNetwork {
    @GET("openLight")
    fun turnOnLed(): Call<Void>

    @GET("openLight")
    fun turnOnSprinkler(): Call<Void>

    @GET("closeLight")
    fun turnOffLed(): Call<Void>

    @GET("closeLight")
    fun turnOffSprinkler(): Call<Void>

    @GET("GetAllWeatherInfo")
    fun getWeatherInfo(): Call<List<WeatherInfo>>

    @GET("login")
    fun login(@Query("username") username: String, @Query("password") password: String): Call<Boolean>
}
