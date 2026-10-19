
package com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.adapter.NewsAdapter
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.factory.ApiServiceFactory
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.factory.ViewModelFactory
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.repository.NewsRepository
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.repository.WeatherRepository
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.viewmodel.NewsViewModel
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.viewmodel.WeatherViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivityFactory : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    private lateinit var tvNewsCity: TextView
    private lateinit var rvNews: RecyclerView
    private lateinit var tvNewsStatus: TextView
    private lateinit var bottomNavigation: BottomNavigationView

    private var cityName: String = ""
    private var countryName: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_news_mvvmand_repository_task16
        )

        initializeViews()
        receiveWeatherData()
        initializeViewModel()
        initializeRecyclerView()
        observeNews()
        setupBottomNavigation()
        displayLocation()
        loadNews()
    }

    private fun initializeViews() {
        tvNewsCity = findViewById(R.id.tvNewsLocation)
        rvNews = findViewById(R.id.recyclerViewNews)
        tvNewsStatus = findViewById(R.id.tvNewsStatus)
        bottomNavigation = findViewById(R.id.bottomNavigation)
    }

    private fun receiveWeatherData() {
        cityName = intent.getStringExtra("cityName").orEmpty()
        countryName = intent.getStringExtra("countryName").orEmpty()
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)
    }

    private fun initializeViewModel() {
        val weatherApi = ApiServiceFactory.createWeatherService()
        val newsApi = ApiServiceFactory.createNewsService()

        val weatherRepository = WeatherRepository(weatherApi)
        val newsRepository = NewsRepository(newsApi)

        val factory = ViewModelFactory(
            weatherRepository,
            newsRepository
        )

        viewModel = ViewModelProvider(
            this,
            factory
        )[NewsViewModel::class.java]
    }

    private fun initializeRecyclerView() {
        newsAdapter = NewsAdapter()

        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.adapter = newsAdapter
    }

    private fun displayLocation() {
        tvNewsCity.text = when {
            cityName.isNotBlank() && countryName.isNotBlank() ->
                "📍 $cityName, $countryName"

            cityName.isNotBlank() ->
                "📍 $cityName"

            else -> "Latest News"
        }
    }

    private fun loadNews() {
        if (cityName.isBlank()) {
            tvNewsStatus.text = "City name is missing"

            Toast.makeText(
                this,
                "City name is missing. Please open Weather first.",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        tvNewsStatus.text = "Loading news..."

        viewModel.loadNews(cityName)
    }

    private fun observeNews() {
        viewModel.newsData.observe(this) { news ->
            newsAdapter.updateData(news.articles)

            tvNewsStatus.text = if (news.articles.isEmpty()) {
                "No news articles found"
            } else {
                "${news.articles.size} news articles loaded"
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            if (!message.isNullOrBlank()) {
                tvNewsStatus.text = message

                Toast.makeText(
                    this,
                    message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigation.selectedItemId = R.id.nav_news

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_news -> true

                R.id.nav_weather -> {
                    val weatherIntent = Intent(
                        this,
                        WeatherActivityFactory::class.java
                    ).apply {
                        putExtra("cityName", cityName)
                        putExtra("countryName", countryName)
                        putExtra("latitude", latitude)
                        putExtra("longitude", longitude)
                    }

                    startActivity(weatherIntent)
                    finish()

                    true
                }

                else -> false
            }
        }
    }
}
