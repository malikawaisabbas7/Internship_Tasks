package com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.adapter.ForecastAdapter
import com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.api.RetrofitClient
import com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.model.ForecastModel
import com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.model.WeatherResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherActivitySingleton : AppCompatActivity() {

    private lateinit var tvLocation: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvWeatherCondition: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvWindSpeed: TextView

    private lateinit var recyclerViewForecast: RecyclerView
    private lateinit var forecastAdapter: ForecastAdapter
    private val forecastList = ArrayList<ForecastModel>()

    private lateinit var bottomNavigation: BottomNavigationView

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var cityName: String = ""
    private var countryName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_weather_singleton_task18
        )

        tvLocation = findViewById(R.id.tvLocation)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvWeatherCondition = findViewById(R.id.tvWeatherCondition)
        tvHumidity = findViewById(R.id.tvHumidity)
        tvWindSpeed = findViewById(R.id.tvWindSpeed)

        recyclerViewForecast = findViewById(R.id.recyclerViewForecast)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        recyclerViewForecast.layoutManager =
            LinearLayoutManager(this)

        forecastAdapter = ForecastAdapter(forecastList)
        recyclerViewForecast.adapter = forecastAdapter

        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)

        if (latitude == 0.0 && longitude == 0.0) {
            tvLocation.text = "Location coordinates are missing."
            Toast.makeText(
                this,
                "Unable to determine your location.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        tvLocation.text = "Getting location..."

        getCityAndCountry()
        getWeather(latitude, longitude)
        setupBottomNavigation()
    }

    private fun getCityAndCountry() {

        Thread {
            try {
                val geocoder = Geocoder(this)

                val addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
                )

                runOnUiThread {
                    if (!addresses.isNullOrEmpty()) {

                        val address = addresses[0]

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
                        cityName = "Unknown City"
                        countryName = "Unknown Country"

                        tvLocation.text = "📍 Unknown Location"
                    }
                }

            } catch (e: Exception) {

                runOnUiThread {
                    cityName = "Unknown City"
                    countryName = "Unknown Country"

                    tvLocation.text = "📍 Location unavailable"

                    Toast.makeText(
                        this,
                        "Unable to detect city name.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }

    private fun getWeather(
        latitude: Double,
        longitude: Double
    ) {

        val call = RetrofitClient.weatherApi.getCurrentWeather(
            latitude = latitude,
            longitude = longitude
        )

        call.enqueue(object : Callback<WeatherResponse> {

            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {

                if (response.isSuccessful) {

                    val weather = response.body()

                    if (weather != null) {

                        tvTemperature.text =
                            "${weather.current.temperature_2m}°C"

                        tvHumidity.text =
                            "Humidity: ${weather.current.relative_humidity_2m}%"

                        tvWindSpeed.text =
                            "Wind: ${weather.current.wind_speed_10m} km/h"

                        tvWeatherCondition.text =
                            getWeatherCondition(
                                weather.current.weather_code
                            )

                        forecastList.clear()

                        val numberOfDays = minOf(
                            7,
                            weather.daily.time.size,
                            weather.daily.temperature_2m_max.size,
                            weather.daily.temperature_2m_min.size,
                            weather.daily.weather_code.size
                        )

                        for (i in 0 until numberOfDays) {

                            val forecast = ForecastModel(
                                date = formatDate(
                                    weather.daily.time[i],
                                    i
                                ),
                                maxTemperature =
                                    weather.daily.temperature_2m_max[i],
                                minTemperature =
                                    weather.daily.temperature_2m_min[i],
                                condition = getWeatherCondition(
                                    weather.daily.weather_code[i]
                                )
                            )

                            forecastList.add(forecast)
                        }

                        forecastAdapter.notifyDataSetChanged()

                    } else {
                        showWeatherError("Weather data is empty.")
                    }

                } else {
                    showWeatherError(
                        "Unable to load weather. Error: ${response.code()}"
                    )
                }
            }

            override fun onFailure(
                call: Call<WeatherResponse>,
                t: Throwable
            ) {
                showWeatherError(
                    "Internet or API error. Please try again."
                )
            }
        })
    }

    private fun showWeatherError(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun formatDate(
        date: String,
        position: Int
    ): String {

        if (position == 0) {
            return "Today"
        }

        return try {
            val inputFormat = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            )

            val outputFormat = SimpleDateFormat(
                "MMM d",
                Locale.getDefault()
            )

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

    private fun getWeatherCondition(
        weatherCode: Int
    ): String {

        return when (weatherCode) {
            0 ->
                "Clear Sky"
            1 ->
                "Mainly Clear"
            2 ->
                "Partly Cloudy"
            3 ->
                "Overcast"
            45, 48 ->
                "Foggy"
            51, 53, 55 ->
                "Drizzle"
            56, 57 ->
                "Freezing Drizzle"
            61, 63, 65 ->
                "Rain"
            66, 67 ->
                "Freezing Rain"
            71, 73, 75, 77 ->
                "Snow"
            80, 81, 82 ->
                "Rain Showers"
            85, 86 ->
                "Snow Showers"
            95 ->
                "Thunderstorm"
            96, 99 ->
                "Thunderstorm with Hail"
            else ->
                "Unknown Weather"
        }
    }

    private fun setupBottomNavigation() {

        bottomNavigation.selectedItemId = R.id.nav_weather

        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_weather -> {
                    true
                }

                R.id.nav_news -> {

                    val intent = Intent(
                        this,
                        NewsActivitySingleton::class.java
                    )

                    intent.putExtra(
                        NewsActivitySingleton.EXTRA_CITY_NAME,
                        cityName
                    )

                    intent.putExtra(
                        "countryName",
                        countryName
                    )

                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)

                    startActivity(intent)

                    true
                }

                else -> false
            }
        }
    }
}