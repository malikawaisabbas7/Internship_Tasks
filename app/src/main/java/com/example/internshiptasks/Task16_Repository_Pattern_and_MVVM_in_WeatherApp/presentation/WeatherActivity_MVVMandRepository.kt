package com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.presentation

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.adapter.ForecastAdapter
import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.model.ForecastModel
import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.model.WeatherResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherActivity_MVVMandRepository : AppCompatActivity() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var forecastAdapter: ForecastAdapter

    private lateinit var tvLocation: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvWeatherCondition: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvWindSpeed: TextView
    private lateinit var recyclerViewForecast: RecyclerView
    private lateinit var bottomNavigation: BottomNavigationView

    private var latitude = 0.0
    private var longitude = 0.0
    private var cityName = ""
    private var countryName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_mvvmand_repository_task16)

        tvLocation = findViewById(R.id.tvLocation)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvWeatherCondition = findViewById(R.id.tvWeatherCondition)
        tvHumidity = findViewById(R.id.tvHumidity)
        tvWindSpeed = findViewById(R.id.tvWindSpeed)
        recyclerViewForecast = findViewById(R.id.recyclerViewForecast)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)
        cityName = intent.getStringExtra("cityName").orEmpty()
        countryName = intent.getStringExtra("countryName").orEmpty()

        forecastAdapter = ForecastAdapter(arrayListOf())
        recyclerViewForecast.layoutManager = LinearLayoutManager(this)
        recyclerViewForecast.adapter = forecastAdapter

        recyclerViewForecast.setHasFixedSize(true)

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        observeWeather()
        setupBottomNavigation()

        if (cityName.isNotBlank()) {
            displayLocationName()
        } else {
            tvLocation.text = "📍 Finding location..."
            resolveCityName()
        }

        if (latitude in -90.0..90.0 &&
            longitude in -180.0..180.0 &&
            (latitude != 0.0 || longitude != 0.0)
        ) {
            viewModel.loadWeather(latitude, longitude)
        } else {
            Toast.makeText(
                this,
                "Valid location coordinates were not received",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun observeWeather() {
        viewModel.weatherData.observe(this) { weather ->
            displayWeather(weather)
            displayForecast(weather)
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG).show()
        }
    }

    private fun displayWeather(weather: WeatherResponse) {
        val current = weather.current

        tvTemperature.text = "${current.temperature_2m} °C"
        tvHumidity.text = "Humidity: ${current.relative_humidity_2m}%"
        tvWindSpeed.text = "Wind: ${current.wind_speed_10m} km/h"

        tvWeatherCondition.text =
            getWeatherDescription(current.weather_code)
    }

    private fun displayForecast(weather: WeatherResponse) {

        val daily = weather.daily

        if (daily == null) {
            forecastAdapter.updateData(emptyList())

            Toast.makeText(
                this,
                "Daily forecast data is unavailable",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        val count = minOf(
            daily.time.size,
            daily.temperature_2m_max.size,
            daily.temperature_2m_min.size,
            daily.weather_code.size
        )

        val forecastList = ArrayList<ForecastModel>()

        for (index in 0 until count) {
            forecastList.add(
                ForecastModel(
                    date =
                        formatForecastDate(daily.time[index]),
                    maxTemperature =
                        daily.temperature_2m_max[index],
                    minTemperature =
                        daily.temperature_2m_min[index],
                    condition =
                        getWeatherDescription(
                        daily.weather_code[index]
                    )
                )
            )
        }

        forecastAdapter.updateData(forecastList)

        if (forecastList.isEmpty()) {
            Toast.makeText(
                this,
                "No forecast data was returned by the API",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun formatForecastDate(date: String): String {
        return try {
            val inputFormat = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            )

            val outputFormat = SimpleDateFormat(
                "EEE, dd MMM",
                Locale.getDefault()
            )

            val parsedDate: Date? = inputFormat.parse(date)

            if (parsedDate != null) {
                outputFormat.format(parsedDate)
            } else {
                date
            }
        } catch (e: Exception) {
            date
        }
    }

    private fun getWeatherDescription(code: Int): String {
        return when (code) {
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
            71, 73, 75 ->

                "Snowfall"
            77 ->

                "Snow Grains"
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

    private fun resolveCityName() {
        if (latitude == 0.0 && longitude == 0.0) {
            tvLocation.text = "📍 Location unavailable"
            return
        }

        Thread {
            try {
                val geocoder = Geocoder(this, Locale.getDefault())

                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
                )

                val address = addresses?.firstOrNull()
                val resolvedCity = address?.locality
                    ?: address?.subAdminArea
                    ?: address?.adminArea
                    ?: ""

                val resolvedCountry = address?.countryName.orEmpty()

                runOnUiThread {
                    cityName = resolvedCity
                    countryName = resolvedCountry
                    displayLocationName()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    tvLocation.text = "📍 Unable to determine location"
                }
            }
        }.start()
    }

    private fun displayLocationName() {
        tvLocation.text = when {
            cityName.isNotBlank() && countryName.isNotBlank() ->
                "📍 $cityName, $countryName"

            cityName.isNotBlank() ->
                "📍 $cityName"

            else ->
                "📍 Location unavailable"
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigation.selectedItemId = R.id.nav_weather

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_weather -> true

                R.id.nav_news -> {
                    if (cityName.isBlank()) {
                        Toast.makeText(
                            this,
                            "Please wait while your location is determined",
                            Toast.LENGTH_SHORT
                        ).show()
                        false
                    } else {
                        val newsIntent = Intent(
                            this,
                            NewsActivity_MVVMandRepository::class.java
                        )

                        newsIntent.putExtra("cityName", cityName)
                        newsIntent.putExtra("countryName", countryName)
                        newsIntent.putExtra("latitude", latitude)
                        newsIntent.putExtra("longitude", longitude)

                        startActivity(newsIntent)
                        true
                    }
                }

                else -> false
            }
        }
    }
}
