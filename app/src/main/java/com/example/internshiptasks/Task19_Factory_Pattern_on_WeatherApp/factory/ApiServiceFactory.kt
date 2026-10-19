
package com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.factory

import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.api.NewsApiService
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.api.RetrofitClient
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.api.WeatherApiService

object ApiServiceFactory {

    fun createWeatherService(): WeatherApiService {
        return RetrofitClient.weatherRetrofit
            .create(WeatherApiService::class.java)
    }

    fun createNewsService(): NewsApiService {
        return RetrofitClient.newsRetrofit
            .create(NewsApiService::class.java)
    }
}
