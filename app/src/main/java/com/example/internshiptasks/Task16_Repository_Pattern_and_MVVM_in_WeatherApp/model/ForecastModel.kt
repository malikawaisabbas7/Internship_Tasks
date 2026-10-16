package com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.model

data class ForecastModel(
    val date: String,
    val maxTemperature: Double,
    val minTemperature: Double,
    val condition: String,
)