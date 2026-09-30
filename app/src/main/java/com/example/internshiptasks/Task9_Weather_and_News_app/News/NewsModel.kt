package com.example.internshiptasks.Task9_Weather_and_News_app.News

data class NewsModel(
    val title: String,
    val description: String,
    val url: String,
    val image: String,
    val publishedAt: String,
    val source: NewsSource
)

data class NewsSource(
    val name: String,
    val url: String
)