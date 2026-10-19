package com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.model

data class NewsResponse(
    val totalArticles: Int,
    val articles: List<NewsArticle>
)

data class NewsArticle(
    val title: String,
    val description: String?,
    val content: String?,
    val url: String,
    val image: String?,
    val publishedAt: String,
    val source: NewsSource
)

data class NewsSource(
    val name: String,
    val url: String
)