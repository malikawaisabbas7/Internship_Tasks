package com.example.internshiptasks.Task14_MVI_on_WeatherApp

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.adapter.ForecastAdapter
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.mvi.weather.WeatherIntent
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.mvi.weather.WeatherState
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.mvi.weather.WeatherViewModel
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.model.ForecastModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class WeatherActivityMVI : AppCompatActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()

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
        setContentView(R.layout.activity_weather_mvi_task14)

        tvLocation = findViewById(R.id.tvLocation)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvWeatherCondition = findViewById(R.id.tvWeatherCondition)
        tvHumidity = findViewById(R.id.tvHumidity)
        tvWindSpeed = findViewById(R.id.tvWindSpeed)

        recyclerViewForecast = findViewById(R.id.recyclerViewForecast)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)

        cityName = intent.getStringExtra("cityName") ?: ""
        countryName = intent.getStringExtra("countryName") ?: ""

        forecastAdapter = ForecastAdapter(forecastList)

        recyclerViewForecast.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )

        recyclerViewForecast.adapter = forecastAdapter

        tvLocation.text = getLocationLabel()

        observeWeatherState()
        setupBottomNavigation()

        if (latitude == 0.0 && longitude == 0.0) {
            tvLocation.text = "📍 Location coordinates unavailable"
            tvWeatherCondition.text = "Unable to get location"
            Toast.makeText(
                this,
                "Please allow location access and try again.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        getCityAndCountry()
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

                        cityName = address.locality
                            ?: address.subAdminArea
                                    ?: address.subLocality
                                    ?: "Unknown City"

                        countryName = address.countryName
                            ?: "Unknown Country"

                        tvLocation.text = getLocationLabel()
                    } else {
                        tvLocation.text = getLocationLabel()
                    }

                    loadWeather()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    tvLocation.text = getLocationLabel()

                    Toast.makeText(
                        this,
                        "City name unavailable. Loading weather...",
                        Toast.LENGTH_SHORT
                    ).show()

                    loadWeather()
                }
            }
        }.start()
    }

    private fun getLocationLabel(): String {
        return when {
            cityName.isNotBlank() && countryName.isNotBlank() ->
                "📍 $cityName, $countryName"

            cityName.isNotBlank() ->
                "📍 $cityName"

            countryName.isNotBlank() ->
                "📍 $countryName"

            else ->
                "📍 Detecting location..."
        }
    }

    private fun loadWeather() {
        weatherViewModel.onIntent(
            WeatherIntent.LoadWeather(
                latitude = latitude,
                longitude = longitude,
                cityName = cityName
            )
        )
    }

    private fun observeWeatherState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherViewModel.state.collect { state ->
                    renderWeatherState(state)
                }
            }
        }
    }

    private fun renderWeatherState(state: WeatherState) {
        tvLocation.text = getLocationLabel()

        if (state.isLoading) {
            tvWeatherCondition.text = "Loading weather..."
            return
        }

        if (state.error != null) {
            tvWeatherCondition.text = state.error

            Toast.makeText(
                this,
                state.error,
                Toast.LENGTH_LONG
            ).show()

            return
        }

        tvTemperature.text = state.temperature
        tvWeatherCondition.text = state.weatherCondition
        tvHumidity.text = "Humidity: ${state.humidity}"
        tvWindSpeed.text = "Wind: ${state.windSpeed}"

        forecastList.clear()
        forecastList.addAll(state.forecast)
        forecastAdapter.notifyDataSetChanged()
    }

    private fun setupBottomNavigation() {
        bottomNavigation.selectedItemId = R.id.nav_weather

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_weather -> true

                R.id.nav_news -> {
                    val newsIntent = Intent(
                        this,
                        NewsActivityMVI::class.java
                    )

                    newsIntent.putExtra("cityName", cityName)
                    newsIntent.putExtra("countryName", countryName)
                    newsIntent.putExtra("latitude", latitude)
                    newsIntent.putExtra("longitude", longitude)

                    startActivity(newsIntent)
                    true
                }

                else -> false
            }
        }
    }
}
