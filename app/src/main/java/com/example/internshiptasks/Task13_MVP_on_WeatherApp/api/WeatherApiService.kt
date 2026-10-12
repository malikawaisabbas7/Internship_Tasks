package com.example.internshiptasks.Task13_MVP_on_WeatherApp.api

import com.example.internshiptasks.Task13_MVP_on_WeatherApp.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("v1/forecast")
    fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String,
        @Query("daily") daily: String,
        @Query("timezone") timezone: String,
    ): Call<WeatherResponse>
}