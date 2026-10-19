
package com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val WEATHER_BASE_URL =
        "https://api.open-meteo.com/"

    private const val NEWS_BASE_URL =
        "https://gnews.io/api/v4/"

    val weatherRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val newsRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
