package com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.repository

import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.api.RetrofitClient
import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.model.ForecastModel
import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    fun getWeather(
        latitude: Double,
        longitude: Double,
        onSuccess: (WeatherResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        RetrofitClient.weatherApi.getWeather(
            latitude = latitude,
            longitude = longitude
        ).enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val weather = response.body()

                    if (weather != null) {
                        onSuccess(weather)
                    } else {
                        onError("Weather data is empty")
                    }
                } else {
                    onError("Weather request failed: ${response.code()}")
                }
            }

            override fun onFailure(
                call: Call<WeatherResponse>,
                throwable: Throwable
            ) {
                onError(
                    throwable.message ?: "Unable to load weather"
                )
            }

        })
    }
}
