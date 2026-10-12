package com.example.internshiptasks.Task13_MVP_on_WeatherApp.contract

import com.example.internshiptasks.Task13_MVP_on_WeatherApp.model.WeatherResponse

interface WeatherContract{

    interface View{
        fun showWeather(weatherResponse: WeatherResponse)
        fun showError(message: String)
    }

    interface Presenter{
        fun getWeather(latitute: Double, longitude: Double)

    }
}