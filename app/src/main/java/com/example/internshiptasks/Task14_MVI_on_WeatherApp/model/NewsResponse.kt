package com.example.internshiptasks.Task14_MVI_on_WeatherApp.model


data class NewsResponse(
    val totalArticles: Int,
    val articles: List<NewsArticle>
)


data class NewsSource(
    val name: String,
    val url: String
)