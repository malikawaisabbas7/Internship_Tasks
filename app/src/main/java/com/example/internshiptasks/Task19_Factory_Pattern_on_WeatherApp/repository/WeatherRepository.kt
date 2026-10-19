
package com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.repository

import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.api.WeatherApiService
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.model.WeatherResponse

class WeatherRepository(
    private val apiService: WeatherApiService
) {

    suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): WeatherResponse {
        return apiService.getWeather(
            latitude = latitude,
            longitude = longitude
        )
    }
}
