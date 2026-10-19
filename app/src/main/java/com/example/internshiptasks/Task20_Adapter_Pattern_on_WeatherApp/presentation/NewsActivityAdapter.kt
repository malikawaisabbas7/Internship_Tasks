
package com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.BuildConfig
import com.example.internshiptasks.R
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.adapter.NewsDataAdapter
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.adapter.NewsRecyclerAdapter
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.api.NewsApiService
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.api.RetrofitClient
import kotlinx.coroutines.launch

class NewsActivityAdapter : AppCompatActivity() {

    private lateinit var newsApiService: NewsApiService
    private lateinit var newsRecyclerAdapter: NewsRecyclerAdapter

    private lateinit var tvNewsCity: TextView
    private lateinit var rvNews: RecyclerView
    private lateinit var tvNewsStatus: TextView

    private var cityName: String = ""
    private var countryName: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_news_mvvmand_repository_task16)

        initializeViews()
        receiveWeatherData()
        initializeApiService()
        initializeRecyclerView()
        displayLocation()
        setupBottomNavigation()
        loadNews()
    }

    private fun initializeViews() {
        tvNewsCity = findViewById(R.id.tvNewsLocation)
        rvNews = findViewById(R.id.recyclerViewNews)
        tvNewsStatus = findViewById(R.id.tvNewsStatus)
    }

    private fun receiveWeatherData() {
        cityName = intent.getStringExtra("cityName").orEmpty()
        countryName = intent.getStringExtra("countryName").orEmpty()
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)
    }

    private fun initializeApiService() {
        newsApiService = RetrofitClient.newsRetrofit
            .create(NewsApiService::class.java)
    }

    private fun initializeRecyclerView() {
        newsRecyclerAdapter = NewsRecyclerAdapter(
            emptyList()
        ) { article ->
            openArticle(article.articleUrl)
        }

        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.adapter = newsRecyclerAdapter
        rvNews.setHasFixedSize(true)
    }

    private fun displayLocation() {
        tvNewsCity.text = when {
            cityName.isNotBlank() && countryName.isNotBlank() ->
                "📍 $cityName, $countryName"

            cityName.isNotBlank() ->
                "📍 $cityName"

            else ->
                "Latest News"
        }
    }

    private fun loadNews() {
        if (cityName.isBlank()) {
            tvNewsStatus.text = "City name is missing"

            Toast.makeText(
                this,
                "Please open Weather first to provide your location.",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        tvNewsStatus.text = "Loading news..."

        lifecycleScope.launch {
            try {
                val response = newsApiService.getNews(
                    query = cityName,
                    language = "en",
                    country = "pk",
                    maxArticles = 10,
                    sortBy = "publishedAt",
                    apiKey = BuildConfig.GNEWS_API_KEY
                )

                val newsDataAdapter = NewsDataAdapter(response)
                val displayArticles =
                    newsDataAdapter.getDisplayArticles()

                newsRecyclerAdapter.updateArticles(displayArticles)

                tvNewsStatus.text = if (displayArticles.isEmpty()) {
                    "No news articles found"
                } else {
                    "${displayArticles.size} news articles loaded"
                }

            } catch (e: Exception) {
                tvNewsStatus.text = "Unable to load news"

                Toast.makeText(
                    this@NewsActivityAdapter,
                    "Unable to load news: ${
                        e.localizedMessage ?: "Please try again"
                    }",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun openArticle(articleUrl: String) {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(articleUrl)
            )
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "No app is available to open this article",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigation =
            findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(
                R.id.bottomNavigation
            )

        bottomNavigation.selectedItemId = R.id.nav_news

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_news -> true

                R.id.nav_weather -> {
                    val weatherIntent = Intent(
                        this,
                        WeatherActivityAdapter::class.java
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
