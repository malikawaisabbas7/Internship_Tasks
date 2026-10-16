package com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.presentation



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.repository.NewsRepository
import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.model.NewsResponse

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository()

    private val _newsData = MutableLiveData<NewsResponse>()
    val newsData: LiveData<NewsResponse>
        get() = _newsData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun loadNews(cityName: String) {
        if (cityName.isBlank()) {
            _errorMessage.value = "City name is required"
            return
        }

        repository.getNews(
            cityName = cityName,
            onSuccess = { news ->
                _newsData.value = news
            },
            onError = { error ->
                _errorMessage.value = error
            }
        )
    }
}
