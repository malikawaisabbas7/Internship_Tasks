package com.example.internshiptasks.Task12_MVC_on_WeatherApp.controller

import com.example.internshiptasks.Task12_MVC_on_WeatherApp.model.NewsResponse
import com.example.internshiptasks.Task12_MVC_on_WeatherApp.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsController {

    fun getNews(
        cityName: String,
        callback: NewsCallback
    ) {

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

                if (response.isSuccessful && response.body() != null) {

                    callback.onSuccess(
                        response.body()!!
                    )

                } else {

                    callback.onError(
                        "Failed to load news. Error code: ${response.code()}"
                    )
                }
            }

            override fun onFailure(
                call: Call<NewsResponse>,
                t: Throwable
            ) {

                callback.onError(
                    t.message ?: "Something went wrong"
                )
            }
        })
    }

    interface NewsCallback {

        fun onSuccess(
            newsResponse: NewsResponse
        )

        fun onError(
            message: String
        )
    }
}