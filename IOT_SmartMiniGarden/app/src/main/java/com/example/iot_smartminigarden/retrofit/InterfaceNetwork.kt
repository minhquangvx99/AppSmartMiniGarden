package com.example.iot_smartminigarden.retrofit

import com.example.iot_smartminigarden.model.Led
import com.example.iot_smartminigarden.model.Sprinkler
import com.example.iot_smartminigarden.model.StatusInfo
import com.example.iot_smartminigarden.model.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

public interface InterfaceNetwork {
    @PUT("openLight")
    fun turnOnLed(@Query("id") id: Float, @Query("statusLed") statusLed: String): Call<Void>

    @PUT("openLight")
    fun turnOnSprinkler(@Query("id") id: Float, @Query("statusLed") statusLed: String): Call<Void>

    @PUT("openLight")
    fun turnOffLed(@Query("id") id: Float, @Query("statusLed") statusLed: String): Call<Void>

    @PUT("openLight")
    fun turnOffSprinkler(@Query("id") id: Float, @Query("statusLed") statusLed: String): Call<Void>

    @GET("GetWeatherNow")
    fun getWeatherInfo(): Call<WeatherInfo>

    @GET("controls")
    fun controls(): Call<List<StatusInfo>>

    @GET("login")
    fun login(@Query("username") username: String, @Query("password") password: String): Call<Boolean>
}
