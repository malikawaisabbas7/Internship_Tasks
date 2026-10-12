package com.example.internshiptasks.Task13_MVP_on_WeatherApp.presenter

import com.example.internshiptasks.Task13_MVP_on_WeatherApp.api.RetrofitClient
import com.example.internshiptasks.Task13_MVP_on_WeatherApp.contract.NewsContract
import com.example.internshiptasks.Task13_MVP_on_WeatherApp.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsPresenter(
    private val view: NewsContract.View
) : NewsContract.Presenter {

    override fun getNews(cityName: String) {

        view.showLoading()

        RetrofitClient.newsApi.getNews(
            query = cityName,
            language = "en",
            country = "pk",
            maxArticles = 10,
            sortBy = "publishedAt",
            apiKey = "6fa25f5376f51c272aac8fed019c8e77"
        ).enqueue(object : Callback<NewsResponse> {

            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {

                if (response.isSuccessful &&
                    response.body() != null
                ) {
                    view.showNews(response.body()!!)
                } else {
                    view.showError(
                        "Failed to load news. Error: ${response.code()}"
                    )
                }
            }

            override fun onFailure(
                call: Call<NewsResponse>,
                t: Throwable
            ) {
                view.showError(
                    t.message ?: "Something went wrong"
                )
            }
        })
    }
}