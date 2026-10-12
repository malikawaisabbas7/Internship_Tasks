package com.example.internshiptasks.Task14_MVI_on_WeatherApp.mvi.weather

sealed class WeatherIntent {

    data class LoadWeather(
        val latitude: Double,
        val longitude: Double,
        val cityName: String
    ) : WeatherIntent()

    data class RefreshWeather(
        val latitude: Double,
        val longitude: Double,
        val cityName: String
    ) : WeatherIntent()
}
