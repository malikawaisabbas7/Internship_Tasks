package com.example.internshiptasks.Task13_MVP_on_WeatherApp.api

import com.example.internshiptasks.Task13_MVP_on_WeatherApp.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("search")
    fun getNews(
        @Query("q") query: String,
        @Query("lang") language: String,
        @Query("country") country: String,
        @Query("max") maxArticles: Int,
        @Query("sortby") sortBy: String,
        @Query("apikey") apiKey: String
    ): Call<NewsResponse>
}