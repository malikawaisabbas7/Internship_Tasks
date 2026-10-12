package com.example.internshiptasks.Task14_MVI_on_WeatherApp.mvi.news

import androidx.lifecycle.ViewModel
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.api.RetrofitClient
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.model.NewsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    private val _state = MutableStateFlow(NewsState())

    val state: StateFlow<NewsState>
        get() = _state

    fun onIntent(intent: NewsIntent) {
        when (intent) {

            is NewsIntent.LoadNews -> {
                getNews(intent.cityName)
            }

            is NewsIntent.RefreshNews -> {
                getNews(intent.cityName)
            }
        }
    }

    private fun getNews(cityName: String) {

        if (cityName.isBlank() || cityName == "Unknown City") {
            _state.value = NewsState(
                error = "City name is unavailable. Please check your location."
            )
            return
        }

        _state.value = _state.value.copy(
            isLoading = true,
            error = null
        )

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

                    val newsResponse = response.body()!!

                    _state.value = NewsState(
                        isLoading = false,
                        articles = newsResponse.articles,
                        error = null
                    )

                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Unable to load news. Error code: ${response.code()}"
                    )
                }
            }

            override fun onFailure(
                call: Call<NewsResponse>,
                t: Throwable
            ) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = t.message ?: "Something went wrong while loading news."
                )
            }
        })
    }
}