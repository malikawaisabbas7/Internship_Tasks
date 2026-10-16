package com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.model

data class ForecastModel(
    val date: String,
    val maxTemperature: Double,
    val minTemperature: Double,
    val condition: String,
)