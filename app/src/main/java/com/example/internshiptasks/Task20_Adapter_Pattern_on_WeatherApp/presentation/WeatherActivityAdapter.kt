
package com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.presentation

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.adapter.ForecastRecyclerAdapter
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.adapter.WeatherDataAdapter
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.api.RetrofitClient
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.api.WeatherApiService
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class WeatherActivityAdapter : AppCompatActivity() {

    private lateinit var weatherApiService: WeatherApiService
    private lateinit var forecastAdapter: ForecastRecyclerAdapter

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

        initializeViews()
        receiveLocationData()
        initializeApiService()
        setupForecastRecyclerView()
        setupBottomNavigation()
        displayOrResolveLocation()
        loadWeatherForLocation()
    }

    private fun initializeViews() {
        tvLocation = findViewById(R.id.tvLocation)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvWeatherCondition = findViewById(R.id.tvWeatherCondition)
        tvHumidity = findViewById(R.id.tvHumidity)
        tvWindSpeed = findViewById(R.id.tvWindSpeed)
        recyclerViewForecast = findViewById(R.id.recyclerViewForecast)
        bottomNavigation = findViewById(R.id.bottomNavigation)
    }

    private fun initializeApiService() {
        weatherApiService = RetrofitClient.weatherRetrofit
            .create(WeatherApiService::class.java)
    }

    private fun receiveLocationData() {
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)
        cityName = intent.getStringExtra("cityName").orEmpty()
        countryName = intent.getStringExtra("countryName").orEmpty()
    }

    private fun setupForecastRecyclerView() {
        forecastAdapter = ForecastRecyclerAdapter(emptyList())

        recyclerViewForecast.layoutManager =
            LinearLayoutManager(this)

        recyclerViewForecast.adapter = forecastAdapter
        recyclerViewForecast.setHasFixedSize(true)
    }

    private fun loadWeatherForLocation() {
        val validCoordinates =
            latitude in -90.0..90.0 &&
                    longitude in -180.0..180.0 &&
                    (latitude != 0.0 || longitude != 0.0)

        if (!validCoordinates) {
            tvLocation.text = "📍 Location unavailable"

            Toast.makeText(
                this,
                "Valid location coordinates were not received",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        lifecycleScope.launch {
            try {
                val response = weatherApiService.getWeather(
                    latitude,
                    longitude
                )

                val weatherAdapter = WeatherDataAdapter(response)

                val currentWeather =
                    weatherAdapter.getCurrentWeather()

                tvTemperature.text =
                    "${currentWeather.temperature} °C"

                tvHumidity.text =
                    "Humidity: ${currentWeather.humidity}%"

                tvWindSpeed.text =
                    "Wind: ${currentWeather.windSpeed} km/h"

                tvWeatherCondition.text =
                    currentWeather.condition

                val forecastList = weatherAdapter.getForecast()

                forecastAdapter.updateForecast(forecastList)

                if (forecastList.isEmpty()) {
                    Toast.makeText(
                        this@WeatherActivityAdapter,
                        "No forecast data available",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@WeatherActivityAdapter,
                    "Unable to load weather: ${
                        e.localizedMessage ?: "Please try again"
                    }",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun displayOrResolveLocation() {
        if (cityName.isNotBlank()) {
            displayLocationName()
        } else {
            tvLocation.text = "📍 Finding location..."
            resolveCityName()
        }
    }

    private fun resolveCityName() {
        if (latitude == 0.0 && longitude == 0.0) {
            tvLocation.text = "📍 Location unavailable"
            return
        }

        Thread {
            try {
                val geocoder = Geocoder(
                    this,
                    java.util.Locale.getDefault()
                )

                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
                )

                val address = addresses?.firstOrNull()

                val resolvedCity =
                    address?.locality
                        ?: address?.subAdminArea
                        ?: address?.adminArea
                        ?: ""

                val resolvedCountry =
                    address?.countryName.orEmpty()

                runOnUiThread {
                    cityName = resolvedCity
                    countryName = resolvedCountry
                    displayLocationName()
                }

            } catch (e: Exception) {
                runOnUiThread {
                    tvLocation.text =
                        "📍 Unable to determine location"
                }
            }
        }.start()
    }

    private fun displayLocationName() {
        tvLocation.text = when {
            cityName.isNotBlank() &&
                    countryName.isNotBlank() ->
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
                            NewsActivityAdapter::class.java
                        ).apply {
                            putExtra("cityName", cityName)
                            putExtra("countryName", countryName)
                            putExtra("latitude", latitude)
                            putExtra("longitude", longitude)
                        }

                        startActivity(newsIntent)
                        true
                    }
                }

                else -> false
            }
        }
    }
}
