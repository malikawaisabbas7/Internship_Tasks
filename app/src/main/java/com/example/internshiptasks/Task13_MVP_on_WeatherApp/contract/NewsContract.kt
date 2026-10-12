package com.example.internshiptasks.Task13_MVP_on_WeatherApp.contract

import com.example.internshiptasks.Task13_MVP_on_WeatherApp.model.NewsResponse

interface NewsContract{

    interface View{
        fun showNews(newsResponse: NewsResponse)
        fun showError(message: String)
        fun showLoading()
    }

    interface Presenter{
        fun getNews(cityName: String)

    }

}