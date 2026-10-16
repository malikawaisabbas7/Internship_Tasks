package com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.model

data class WeatherResponse(
    val current: CurrentWeather,
    val daily: DailyWeather
)

data class CurrentWeather(
    val temperature_2m: Double,
    val relative_humidity_2m: Double,
    val wind_speed_10m: Double,
    val weather_code: Int
)

data class DailyWeather(
    val time: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val weather_code: List<Int>
)