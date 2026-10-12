package com.example.internshiptasks.Task12_MVC_on_WeatherApp

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task12_MVC_on_WeatherApp.adapter.ForecastAdapter
import com.example.internshiptasks.Task12_MVC_on_WeatherApp.controller.WeatherController
import com.example.internshiptasks.Task12_MVC_on_WeatherApp.model.ForecastModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherActivity : AppCompatActivity() {

    private lateinit var tvLocation: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvWeatherCondition: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvWindSpeed: TextView

    private lateinit var recyclerViewForecast: RecyclerView
    private lateinit var forecastAdapter: ForecastAdapter
    private val forecastList = ArrayList<ForecastModel>()

    private lateinit var bottomNavigation: BottomNavigationView

    private lateinit var weatherController: WeatherController

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private var cityName: String = ""
    private var countryName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_weather_task12)

        tvLocation = findViewById(R.id.tvLocation)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvWeatherCondition = findViewById(R.id.tvWeatherCondition)
        tvHumidity = findViewById(R.id.tvHumidity)
        tvWindSpeed = findViewById(R.id.tvWindSpeed)

        recyclerViewForecast = findViewById(R.id.recyclerViewForecast)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        recyclerViewForecast.layoutManager =
            LinearLayoutManager(this)

        forecastAdapter =
            ForecastAdapter(forecastList)

        recyclerViewForecast.adapter =
            forecastAdapter

        latitude =
            intent.getDoubleExtra("latitude", 0.0)

        longitude =
            intent.getDoubleExtra("longitude", 0.0)

        weatherController =
            WeatherController()

        tvLocation.text =
            "Getting location..."

        getCityAndCountry()

        getWeatherData()

        setupBottomNavigation()
    }

    private fun getWeatherData() {

        weatherController.getWeather(
            latitude,
            longitude,
            object : WeatherController.WeatherCallback {

                override fun onSuccess(
                    weatherResponse: com.example.internshiptasks.Task12_MVC_on_WeatherApp.model.WeatherResponse
                ) {

                    tvTemperature.text =
                        "${weatherResponse.current.temperature_2m}°C"

                    tvHumidity.text =
                        "Humidity: ${weatherResponse.current.relative_humidity_2m}%"

                    tvWindSpeed.text =
                        "Wind: ${weatherResponse.current.wind_speed_10m} km/h"

                    tvWeatherCondition.text =
                        getWeatherCondition(
                            weatherResponse.current.weather_code
                        )

                    forecastList.clear()

                    val numberOfDays =
                        minOf(
                            7,
                            weatherResponse.daily.time.size
                        )

                    for (i in 0 until numberOfDays) {

                        val forecast =
                            ForecastModel(
                                date = formatDate(
                                    weatherResponse.daily.time[i],
                                    i
                                ),
                                maxTemperature =
                                    weatherResponse.daily.temperature_2m_max[i],
                                minTemperature =
                                    weatherResponse.daily.temperature_2m_min[i],
                                condition =
                                    getWeatherCondition(
                                        weatherResponse.daily.weather_code[i]
                                    )
                            )

                        forecastList.add(forecast)
                    }

                    forecastAdapter.notifyDataSetChanged()
                }

                override fun onError(
                    message: String
                ) {

                    Toast.makeText(
                        this@WeatherActivity,
                        message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    private fun getCityAndCountry() {

        Thread {

            try {

                val geocoder =
                    Geocoder(this)

                val addresses =
                    geocoder.getFromLocation(
                        latitude,
                        longitude,
                        1
                    )

                runOnUiThread {

                    if (!addresses.isNullOrEmpty()) {

                        val address =
                            addresses[0]

                        cityName =
                            address.locality
                                ?: address.subAdminArea
                                        ?: "Unknown City"

                        countryName =
                            address.countryName
                                ?: "Unknown Country"

                        tvLocation.text =
                            "📍 $cityName, $countryName"

                    } else {

                        tvLocation.text =
                            "📍 Unknown Location"
                    }
                }

            } catch (e: Exception) {

                runOnUiThread {

                    tvLocation.text =
                        "📍 Location unavailable"

                    Toast.makeText(
                        this,
                        "Unable to detect city name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }.start()
    }

    private fun formatDate(
        date: String,
        position: Int
    ): String {

        if (position == 0) {
            return "Today"
        }

        return try {

            val inputFormat =
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                )

            val outputFormat =
                SimpleDateFormat(
                    "MMM, d",
                    Locale.getDefault()
                )

            val parsedDate =
                inputFormat.parse(date)

            outputFormat.format(parsedDate!!)

        } catch (e: Exception) {

            date
        }
    }

    private fun getWeatherCondition(
        weatherCode: Int
    ): String {

        return when (weatherCode) {

            0 -> "Clear Sky"

            1, 2, 3 ->
                "Partly Cloudy"

            45, 48 ->
                "Foggy"

            51, 53, 55 ->
                "Drizzle"

            61, 63, 65 ->
                "Rain"

            71, 73, 75 ->
                "Snow"

            80, 81, 82 ->
                "Rain Showers"

            95 ->
                "Thunderstorm"

            96, 99 ->
                "Thunderstorm with Hail"

            else ->
                "Unknown Weather"
        }
    }

    private fun setupBottomNavigation() {

        bottomNavigation.selectedItemId =
            R.id.nav_weather

        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_weather -> {
                    true
                }

                R.id.nav_news -> {

                    val intent =
                        Intent(
                            this,
                            NewsActivity::class.java
                        )

                    intent.putExtra(
                        "cityName",
                        cityName
                    )

                    intent.putExtra(
                        "countryName",
                        countryName
                    )

                    intent.putExtra(
                        "latitude",
                        latitude
                    )

                    intent.putExtra(
                        "longitude",
                        longitude
                    )

                    startActivity(intent)

                    true
                }

                else -> false
            }
        }
    }
}