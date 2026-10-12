package com.example.internshiptasks.Task12_MVC_on_WeatherApp.model

data class ForecastModel(
    val date: String,
    val maxTemperature: Double,
    val minTemperature: Double,
    val condition: String,
)