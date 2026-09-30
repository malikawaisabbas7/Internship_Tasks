package com.example.internshiptasks.Task9_Weather_and_News_app.News

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

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