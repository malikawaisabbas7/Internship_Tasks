package com.example.internshiptasks.Task11_MVVM_on_WeatherApp.repository

import com.example.internshiptasks.Task11_MVVM_on_WeatherApp.api.RetrofitClient
import com.example.internshiptasks.Task11_MVVM_on_WeatherApp.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    fun getWeather(
        latitude: Double,
        longitude: Double,
        callback: WeatherCallback
    ) {

        RetrofitClient.weatherApi.getWeather(
            latitude = latitude,
            longitude = longitude,
            current = "temperature_2m," +
                    "relative_humidity_2m," +
                    "wind_speed_10m," +
                    "weather_code",

            daily = "temperature_2m_max," +
                    "temperature_2m_min," +
                    "weather_code",
            timezone = "auto"
        ).enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {

                if (response.isSuccessful && response.body() != null) {

                    callback.onSuccess(response.body()!!)

                } else {

                    callback.onError("Failed to load weather data")
                }
            }

            override fun onFailure(
                call: Call<WeatherResponse>,
                t: Throwable
            ) {

                callback.onError(
                    t.message ?: "Something went wrong"
                )
            }
        })
    }

    interface WeatherCallback {

        fun onSuccess(weatherResponse: WeatherResponse)

        fun onError(message: String)
    }
}