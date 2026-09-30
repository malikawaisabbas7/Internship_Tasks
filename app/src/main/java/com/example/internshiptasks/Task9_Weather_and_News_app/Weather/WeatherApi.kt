package com.example.internshiptasks.Task9_Weather_and_News_app.Weather

import com.google.type.DayOfWeek
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast")
    fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String,
        @Query("daily") daily: String,
        @Query("timezone") timezone: String,
    ): Call<WeatherResponse>
}
