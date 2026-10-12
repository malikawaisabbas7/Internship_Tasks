package com.example.internshiptasks.Task14_MVI_on_WeatherApp.repository

import com.example.internshiptasks.Task14_MVI_on_WeatherApp.api.RetrofitClient
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.model.NewsResponse
import retrofit2.Call

class NewsRepository {

    fun getNews(cityName: String): Call<NewsResponse> {
        return RetrofitClient.newsApi.getNews(
            query = cityName,
            language = "en",
            country = "pk",
            maxArticles = 10,
            sortBy = "publishedAt",
            apiKey = "6fa25f5376f51c272aac8fed019c8e77"
        )
    }
}
