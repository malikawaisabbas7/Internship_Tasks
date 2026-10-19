
package com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.repository

import com.example.internshiptasks.BuildConfig
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.api.NewsApiService
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.model.NewsResponse


class NewsRepository(
    private val apiService: NewsApiService
) {

    suspend fun getNews(query: String): NewsResponse {
        return apiService.getNews(
            query = query,
            language = "en",
            country = "pk",
            maxArticles = 10,
            sortBy = "publishedAt",
            apiKey = BuildConfig.GNEWS_API_KEY
        )
    }
}
