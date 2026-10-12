package com.example.internshiptasks.Task14_MVI_on_WeatherApp.mvi.weather

import androidx.lifecycle.ViewModel
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.model.ForecastModel
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.model.WeatherResponse
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()

    private val _state = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState>
        get() = _state

    fun onIntent(intent: WeatherIntent) {
        when (intent) {
            is WeatherIntent.LoadWeather -> {
                getWeather(
                    latitude = intent.latitude,
                    longitude = intent.longitude,
                    cityName = intent.cityName
                )
            }

            is WeatherIntent.RefreshWeather -> {
                getWeather(
                    latitude = intent.latitude,
                    longitude = intent.longitude,
                    cityName = intent.cityName
                )
            }
        }
    }

    private fun getWeather(
        latitude: Double,
        longitude: Double,
        cityName: String
    ) {
        _state.value = WeatherState(
            isLoading = true,
            cityName = cityName
        )

        weatherRepository.getWeather(
            latitude = latitude,
            longitude = longitude
        ).enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (!response.isSuccessful) {
                    _state.value = WeatherState(
                        isLoading = false,
                        cityName = cityName,
                        error = "Weather request failed: HTTP ${response.code()}"
                    )
                    return
                }

                val weather = response.body()

                if (weather == null) {
                    _state.value = WeatherState(
                        isLoading = false,
                        cityName = cityName,
                        error = "Weather data is empty."
                    )
                    return
                }

                val current = weather.current
                val daily = weather.daily

                val forecastList = ArrayList<ForecastModel>()

                val count = minOf(
                    7,
                    daily.time.size,
                    daily.temperature_2m_max.size,
                    daily.temperature_2m_min.size,
                    daily.weather_code.size
                )

                for (i in 0 until count) {
                    forecastList.add(
                        ForecastModel(
                            date = formatDate(daily.time[i]),
                            maxTemperature = daily.temperature_2m_max[i],
                            minTemperature = daily.temperature_2m_min[i],
                            condition = getWeatherCondition(
                                daily.weather_code[i]
                            )
                        )
                    )
                }

                _state.value = WeatherState(
                    isLoading = false,
                    cityName = cityName,
                    temperature = "${current.temperature_2m} °C",
                    humidity = "${current.relative_humidity_2m}%",
                    windSpeed = "${current.wind_speed_10m} km/h",
                    weatherCondition = getWeatherCondition(
                        current.weather_code
                    ),
                    forecast = forecastList,
                    error = null
                )
            }

            override fun onFailure(
                call: Call<WeatherResponse>,
                t: Throwable
            ) {
                _state.value = WeatherState(
                    isLoading = false,
                    cityName = cityName,
                    error = "Unable to load weather: ${
                        t.localizedMessage ?: "Check your internet connection."
                    }"
                )
            }
        })
    }

    private fun formatDate(date: String): String {
        return try {
            val inputFormat =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            val outputFormat =
                SimpleDateFormat("EEE, dd MMM", Locale.getDefault())

            val parsedDate = inputFormat.parse(date)

            if (parsedDate != null) {
                outputFormat.format(parsedDate)
            } else {
                date
            }
        } catch (e: Exception) {
            date
        }
    }

    private fun getWeatherCondition(code: Int): String {
        return when (code) {
            0 -> "Clear sky"
            1 -> "Mainly clear"
            2 -> "Partly cloudy"
            3 -> "Overcast"
            45, 48 -> "Fog"
            51, 53, 55 -> "Drizzle"
            56, 57 -> "Freezing drizzle"
            61, 63, 65 -> "Rain"
            66, 67 -> "Freezing rain"
            71, 73, 75 -> "Snowfall"
            77 -> "Snow grains"
            80, 81, 82 -> "Rain showers"
            85, 86 -> "Snow showers"
            95 -> "Thunderstorm"
            96, 99 -> "Thunderstorm with hail"
            else -> "Unknown"
        }
    }
}
