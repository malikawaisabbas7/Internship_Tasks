package com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.model

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