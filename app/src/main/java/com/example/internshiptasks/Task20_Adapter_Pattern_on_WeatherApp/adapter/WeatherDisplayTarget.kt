package com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.adapter

import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.model.ForecastModel
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.model.WeatherDisplayData

interface WeatherDisplayTarget {
    fun getCurrentWeather(): WeatherDisplayData

    fun getForecast(): List<ForecastModel>
}