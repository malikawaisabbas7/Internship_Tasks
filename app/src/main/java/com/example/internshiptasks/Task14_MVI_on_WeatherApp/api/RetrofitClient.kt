package com.example.internshiptasks.Task14_MVI_on_WeatherApp.api

import com.example.internshiptasks.Task14_MVI_on_WeatherApp.api.NewsApiService
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.api.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val WEATHER_BASE_URL =
        "https://api.open-meteo.com/"

    private const val NEWS_BASE_URL =
        "https://gnews.io/api/v4/"

    val weatherApi: com.example.internshiptasks.Task14_MVI_on_WeatherApp.api.WeatherApiService by lazy {

        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(WeatherApiService::class.java)
    }

    val newsApi: com.example.internshiptasks.Task14_MVI_on_WeatherApp.api.NewsApiService by lazy {

        Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(NewsApiService::class.java)
    }
}