package com.example.internshiptasks.Task14_MVI_on_WeatherApp.mvi.news


sealed class NewsIntent {

    data class LoadNews(
        val cityName: String
    ) : NewsIntent()

    data class RefreshNews(
        val cityName: String
    ) : NewsIntent()
}

