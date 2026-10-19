package com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.api

import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("search")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("lang") language: String = "en",
        @Query("country") country: String = "pk",
        @Query("max") maxArticles: Int = 10,
        @Query("sortby") sortBy: String = "publishedAt",
        @Query("apikey") apiKey: String
    ): NewsResponse
}