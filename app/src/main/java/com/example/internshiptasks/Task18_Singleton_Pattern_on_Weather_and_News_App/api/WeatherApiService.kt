
package com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.api

import com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("v1/forecast")
    fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current")
        current: String =
            "temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code",
        @Query("daily")
        daily: String =
            "temperature_2m_max,temperature_2m_min,weather_code",
        @Query("timezone")
        timezone: String = "auto"
    ): Call<WeatherResponse>
}
