
package com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val WEATHER_BASE_URL =
        "https://api.open-meteo.com/"

    private const val NEWS_BASE_URL =
        "https://gnews.io/api/v4/"

    private val weatherRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val newsRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi: WeatherApiService by lazy {
        weatherRetrofit.create(WeatherApiService::class.java)
    }

    val newsApi: NewsApiService by lazy {
        newsRetrofit.create(NewsApiService::class.java)
    }
}
