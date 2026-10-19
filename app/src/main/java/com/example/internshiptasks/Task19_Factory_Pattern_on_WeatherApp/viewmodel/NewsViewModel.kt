package com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.model.NewsResponse
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _newsData = MutableLiveData<NewsResponse>()
    val newsData: LiveData<NewsResponse> = _newsData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadNews(query: String) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val response = repository.getNews(query)
                _newsData.value = response

            } catch (e: Exception) {
                _errorMessage.value =
                    e.message ?: "Unable to load news"

            } finally {
                _isLoading.value = false
            }
        }
    }
}