package com.example.internshiptasks.Task14_MVI_on_WeatherApp.mvi.weather

import com.example.internshiptasks.Task14_MVI_on_WeatherApp.model.ForecastModel

data class WeatherState(
    val isLoading: Boolean = false,
    val cityName: String = "",
    val temperature: String = "--",
    val humidity: String = "--",
    val windSpeed: String = "--",
    val weatherCondition: String = "Unknown",
    val forecast: List<ForecastModel> = emptyList(),
    val error: String? = null
)