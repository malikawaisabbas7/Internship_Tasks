package com.example.internshiptasks.Task11_MVVM_on_WeatherApp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.internshiptasks.Task11_MVVM_on_WeatherApp.model.NewsResponse
import com.example.internshiptasks.Task11_MVVM_on_WeatherApp.repository.NewsRepository

class NewsViewModel : ViewModel() {

    private val repository =
        NewsRepository()

    private val _newsData =
        MutableLiveData<NewsResponse>()

    val newsData: LiveData<NewsResponse>
        get() = _newsData

    private val _errorMessage =
        MutableLiveData<String>()

    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getNews(cityName: String) {

        repository.getNews(
            cityName,
            object : NewsRepository.NewsCallback {

                override fun onSuccess(
                    newsResponse: NewsResponse
                ) {
                    _newsData.postValue(newsResponse)
                }

                override fun onError(
                    message: String
                ) {
                    _errorMessage.postValue(message)
                }
            }
        )
    }
}