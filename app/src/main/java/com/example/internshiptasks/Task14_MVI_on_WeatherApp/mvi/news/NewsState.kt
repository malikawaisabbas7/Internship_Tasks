package com.example.internshiptasks.Task14_MVI_on_WeatherApp.mvi.news


import com.example.internshiptasks.Task14_MVI_on_WeatherApp.model.NewsArticle

data class NewsState(
    val isLoading: Boolean = false,
    val articles: List<NewsArticle> = emptyList(),
    val error: String? = null
)
