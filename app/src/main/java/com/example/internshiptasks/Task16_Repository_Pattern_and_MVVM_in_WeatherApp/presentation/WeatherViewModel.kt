package com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.model.ForecastModel
import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.model.WeatherResponse
import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.repository.WeatherRepository

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse>
        get() = _weatherData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun loadWeather(latitude: Double, longitude: Double) {

        if (latitude !in -90.0..90.0 ||
            longitude !in -180.0..180.0
        ) {
            _errorMessage.value = "Invalid location coordinates"
            return
        }

        repository.getWeather(
            latitude = latitude,
            longitude = longitude,
            onSuccess = { weather ->
                _weatherData.postValue(weather)
            },
            onError = { error ->
                _errorMessage.postValue(error)
            },

        )
    }
}
