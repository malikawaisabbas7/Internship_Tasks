
package com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.api

import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("v1/forecast")
    suspend fun getWeather(
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
    ): WeatherResponse
}
