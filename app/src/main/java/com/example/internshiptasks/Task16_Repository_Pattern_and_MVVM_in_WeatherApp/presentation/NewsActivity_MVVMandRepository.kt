package com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task16_Repository_Pattern_and_MVVM_in_WeatherApp.adapter.NewsAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity_MVVMandRepository : AppCompatActivity() {

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

        tvNewsCity = findViewById(R.id.tvNewsLocation)
        rvNews = findViewById(R.id.recyclerViewNews)
        tvNewsStatus = findViewById(R.id.tvNewsStatus)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        cityName = intent.getStringExtra("cityName").orEmpty()
        countryName = intent.getStringExtra("countryName").orEmpty()
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        newsAdapter = NewsAdapter()
        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.adapter = newsAdapter

        observeNews()

        setupBottomNavigation()

        tvNewsCity.text = if (cityName.isNotBlank()) {
            if (countryName.isNotBlank()) {
                "📍 $cityName, $countryName"
            } else {
                "📍 $cityName"
            }
        } else {
            "Latest News"
        }

        if (cityName.isNotBlank()) {
            tvNewsStatus.text = "Loading news..."
            viewModel.loadNews(cityName)
        } else {
            tvNewsStatus.text = "City name is missing"
            Toast.makeText(
                this,
                "City name is missing. Please open Weather first.",
                Toast.LENGTH_LONG
            ).show()
        }
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
            tvNewsStatus.text = message

            Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setupBottomNavigation() {

        bottomNavigation.selectedItemId = R.id.nav_news

        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_news -> {
                    true
                }

                R.id.nav_weather -> {
                    val weatherIntent = Intent(
                        this,
                        WeatherActivity_MVVMandRepository::class.java
                    )

                    weatherIntent.putExtra("cityName", cityName)
                    weatherIntent.putExtra("countryName", countryName)
                    weatherIntent.putExtra("latitude", latitude)
                    weatherIntent.putExtra("longitude", longitude)

                    startActivity(weatherIntent)

                    finish()

                    true
                }

                else -> false
            }
        }
    }
}
