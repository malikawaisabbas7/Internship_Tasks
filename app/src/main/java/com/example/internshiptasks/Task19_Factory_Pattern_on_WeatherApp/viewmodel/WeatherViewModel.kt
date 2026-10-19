package com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.model.WeatherResponse
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadWeather(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val response = repository.getWeather(
                    latitude,
                    longitude
                )

                _weatherData.value = response

            } catch (e: Exception) {
                _errorMessage.value =
                    e.message ?: "Unable to load weather data"

            } finally {
                _isLoading.value = false
            }
        }
    }
}