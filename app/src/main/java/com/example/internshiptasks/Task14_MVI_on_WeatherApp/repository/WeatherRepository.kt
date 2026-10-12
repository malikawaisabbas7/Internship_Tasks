package com.example.internshiptasks.Task14_MVI_on_WeatherApp.repository

import com.example.internshiptasks.Task14_MVI_on_WeatherApp.api.RetrofitClient
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.model.WeatherResponse
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.repository.WeatherRepository
import retrofit2.Call

class WeatherRepository {

    fun getWeather(
        latitude: Double,
        longitude: Double
    ): Call<WeatherResponse> {

        return RetrofitClient.weatherApi.getWeather(
            latitude = latitude,
            longitude = longitude,
            current = "temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code",
            daily = "temperature_2m_max,temperature_2m_min,weather_code",
            timezone = "auto"
        )
    }
}
