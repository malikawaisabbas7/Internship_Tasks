package com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.repository

import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.api.RetrofitClient
import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    fun getNews(
        cityName: String,
        onSuccess: (NewsResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        RetrofitClient.newsApi.getNews(
            query = cityName,
            language = "en",
            country = "us",
            maxArticles = 10,
            sortBy = "publishedAt",
            apiKey = "6fa25f5376f51c272aac8fed019c8e77"
        ).enqueue(object : Callback<NewsResponse> {

            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                val news = response.body()

                if (response.isSuccessful && news != null) {
                    onSuccess(news)
                } else {
                    onError(
                        "News request failed. HTTP code: ${response.code()}"
                    )
                }
            }

            override fun onFailure(
                call: Call<NewsResponse>,
                throwable: Throwable
            ) {
                onError(
                    throwable.message ?: "Unable to load news"
                )
            }
        })
    }
}
