package com.example.internshiptasks.Task9_Weather_and_News_app.Weather

data class ForecastModel(
    val date: String,
    val maxTemperature: Double,
    val minTemperature: Double,
    val condition: String,
)