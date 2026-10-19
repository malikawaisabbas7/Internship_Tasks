package com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.adapter

import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.model.NewsDisplayData

interface NewsDisplayTarget {
    fun getDisplayArticles(): List<NewsDisplayData>
}