package com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.model

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