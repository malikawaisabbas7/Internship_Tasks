package com.example.internshiptasks.Task13_MVP_on_WeatherApp.presenter

import com.example.internshiptasks.Task13_MVP_on_WeatherApp.api.RetrofitClient
import com.example.internshiptasks.Task13_MVP_on_WeatherApp.contract.WeatherContract
import com.example.internshiptasks.Task13_MVP_on_WeatherApp.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherPresenter(
    private val view: WeatherContract.View
) : WeatherContract.Presenter {

    override fun getWeather(
        latitude: Double,
        longitude: Double
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

                if (response.isSuccessful &&
                    response.body() != null
                ) {
                    view.showWeather(response.body()!!)
                } else {
                    view.showError("Failed to load weather data")
                }
            }

            override fun onFailure(
                call: Call<WeatherResponse>,
                t: Throwable
            ) {
                view.showError(
                    t.message ?: "Something went wrong"
                )
            }
        })
    }
}