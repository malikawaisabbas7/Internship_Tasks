package com.example.internshiptasks.Task14_MVI_on_WeatherApp

import android.content.Intent
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
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.adapter.NewsAdapter
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.mvi.news.NewsIntent
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.mvi.news.NewsState
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.mvi.news.NewsViewModel
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.model.NewsArticle
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class NewsActivityMVI : AppCompatActivity() {

    private lateinit var tvNewsLocation: TextView
    private lateinit var tvNewsStatus: TextView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recyclerViewNews: RecyclerView

    private lateinit var newsAdapter: NewsAdapter
    private val newsList = ArrayList<NewsArticle>()

    private val newsViewModel: NewsViewModel by viewModels()

    private var cityName = "Unknown City"
    private var countryName = "Unknown Country"
    private var latitude = 0.0
    private var longitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_mvi_task14)

        tvNewsLocation = findViewById(R.id.tvNewsLocation)
        tvNewsStatus = findViewById(R.id.tvNewsStatus)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        recyclerViewNews = findViewById(R.id.recyclerViewNews)

        cityName = intent.getStringExtra("cityName") ?: "Unknown City"
        countryName = intent.getStringExtra("countryName") ?: "Unknown Country"
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)

        tvNewsLocation.text = "📍 $cityName, $countryName"

        recyclerViewNews.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsAdapter(newsList)
        recyclerViewNews.adapter = newsAdapter

        setupBottomNavigation()
        observeNewsState()

        newsViewModel.onIntent(NewsIntent.LoadNews(cityName))
    }

    private fun observeNewsState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsViewModel.state.collect { state ->
                    renderNewsState(state)
                }
            }
        }
    }

    private fun renderNewsState(state: NewsState) {
        when {
            state.isLoading -> {
                tvNewsStatus.text = "Loading news..."
            }

            state.error != null -> {
                tvNewsStatus.text = "Unable to load news."
                Toast.makeText(
                    this,
                    state.error,
                    Toast.LENGTH_LONG
                ).show()
            }

            else -> {
                newsList.clear()
                newsList.addAll(state.articles)
                newsAdapter.notifyDataSetChanged()

                tvNewsStatus.text = if (newsList.isEmpty()) {
                    "No local news found."
                } else {
                    "${newsList.size} news articles found."
                }
            }
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigation.selectedItemId = R.id.nav_news

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_weather -> {
                    val weatherIntent = Intent(
                        this,
                        WeatherActivityMVI::class.java
                    )

                    weatherIntent.putExtra("cityName", cityName)
                    weatherIntent.putExtra("countryName", countryName)
                    weatherIntent.putExtra("latitude", latitude)
                    weatherIntent.putExtra("longitude", longitude)

                    startActivity(weatherIntent)
                    finish()
                    true
                }

                R.id.nav_news -> true

                else -> false
            }
        }
    }
}
