package com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.model

data class NewsDisplayData(
    val title: String,
    val description: String,
    val imageUrl: String?,
    val articleUrl: String,
    val sourceName: String,
    val publishedAt: String
)