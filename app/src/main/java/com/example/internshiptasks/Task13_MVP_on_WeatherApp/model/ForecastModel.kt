package com.example.internshiptasks.Task13_MVP_on_WeatherApp.model

data class ForecastModel(
    val date: String,
    val maxTemperature: Double,
    val minTemperature: Double,
    val condition: String,
)