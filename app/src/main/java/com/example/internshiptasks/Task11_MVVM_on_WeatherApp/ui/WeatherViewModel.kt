package com.example.internshiptasks.Task11_MVVM_on_WeatherApp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.internshiptasks.Task11_MVVM_on_WeatherApp.model.WeatherResponse
import com.example.internshiptasks.Task11_MVVM_on_WeatherApp.repository.WeatherRepository

class WeatherViewModel : ViewModel() {

    private val repository =
        WeatherRepository()

    private val _weatherData =
        MutableLiveData<WeatherResponse>()

    val weatherData: LiveData<WeatherResponse>
        get() = _weatherData

    private val _errorMessage =
        MutableLiveData<String>()

    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getWeather(
        latitude: Double,
        longitude: Double
    ) {

        repository.getWeather(
            latitude,
            longitude,
            object : WeatherRepository.WeatherCallback {

                override fun onSuccess(
                    weatherResponse: WeatherResponse
                ) {

                    _weatherData.postValue(
                        weatherResponse
                    )
                }

                override fun onError(
                    message: String
                ) {

                    _errorMessage.postValue(
                        message
                    )
                }
            }
        )
    }
}