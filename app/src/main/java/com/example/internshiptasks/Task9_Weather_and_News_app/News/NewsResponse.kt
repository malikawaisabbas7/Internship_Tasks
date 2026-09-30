package com.example.internshiptasks.Task9_Weather_and_News_app.News

data class NewsResponse(
    val totalArticles: Int,
    val articles: List<NewsModel>
)