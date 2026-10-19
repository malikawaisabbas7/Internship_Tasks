package com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.adapter

import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.model.ForecastModel
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.model.WeatherDisplayData
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.model.WeatherResponse

class WeatherDataAdapter(
    private val response: WeatherResponse
) : WeatherDisplayTarget {

    override fun getCurrentWeather(): WeatherDisplayData {
        val current = response.current

        return WeatherDisplayData(
            temperature = current.temperature_2m,
            humidity = current.relative_humidity_2m,
            windSpeed = current.wind_speed_10m,
            condition = getWeatherCondition(current.weather_code)
        )
    }

    override fun getForecast(): List<ForecastModel> {
        val daily = response.daily ?: return emptyList()

        val count = minOf(
            daily.time.size,
            daily.temperature_2m_max.size,
            daily.temperature_2m_min.size,
            daily.weather_code.size
        )

        return (0 until count).map { index ->
            ForecastModel(
                date = daily.time[index],
                maxTemperature = daily.temperature_2m_max[index],
                minTemperature = daily.temperature_2m_min[index],
                condition = getWeatherCondition(daily.weather_code[index])
            )
        }
    }

    private fun getWeatherCondition(code: Int): String {
        return when (code) {
            0 -> "Clear sky"
            1 -> "Mainly clear"
            2 -> "Partly cloudy"
            3 -> "Overcast"
            45, 48 -> "Fog"
            in 51..57 -> "Drizzle"
            in 61..67 -> "Rain"
            in 71..77 -> "Snow"
            in 80..82 -> "Rain showers"
            in 85..86 -> "Snow showers"
            95, 96, 99 -> "Thunderstorm"
            else -> "Unknown condition"
        }
    }
}
