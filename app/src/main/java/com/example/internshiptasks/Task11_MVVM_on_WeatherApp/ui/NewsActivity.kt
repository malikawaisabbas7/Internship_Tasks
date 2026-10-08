package com.example.internshiptasks.Task11_MVVM_on_WeatherApp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task11_MVVM_on_WeatherApp.adapter.NewsAdapter
import com.example.internshiptasks.Task11_MVVM_on_WeatherApp.model.NewsArticle
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity : AppCompatActivity() {

    private lateinit var tvNewsLocation: TextView
    private lateinit var tvNewsStatus: TextView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recyclerViewNews: RecyclerView

    private lateinit var newsAdapter: NewsAdapter
    private val newsList = ArrayList<NewsArticle>()

    private lateinit var newsViewModel: NewsViewModel

    private var cityName: String = ""
    private var countryName: String = ""

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_news_task11)

        tvNewsLocation = findViewById(R.id.tvNewsLocation)
        tvNewsStatus = findViewById(R.id.tvNewsStatus)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        recyclerViewNews = findViewById(R.id.recyclerViewNews)

        cityName = intent.getStringExtra("cityName")
            ?: "Unknown City"

        countryName = intent.getStringExtra("countryName")
            ?: "Unknown Country"

        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)

        tvNewsLocation.text =
            "📍 $cityName, $countryName"

        recyclerViewNews.layoutManager =
            LinearLayoutManager(this)

        newsAdapter = NewsAdapter(newsList)

        recyclerViewNews.adapter = newsAdapter

        newsViewModel =
            ViewModelProvider(this)[NewsViewModel::class.java]

        observeNewsData()

        newsViewModel.getNews(cityName)

        setupBottomNavigation()
    }

    private fun observeNewsData() {

        tvNewsStatus.text = "Loading news..."

        newsViewModel.newsData.observe(this) { newsResponse ->

            newsList.clear()

            newsList.addAll(
                newsResponse.articles
            )

            newsAdapter.notifyDataSetChanged()

            if (newsList.isEmpty()) {

                tvNewsStatus.text =
                    "No local news found."

            } else {

                tvNewsStatus.text =
                    "${newsList.size} news articles found."
            }
        }

        newsViewModel.errorMessage.observe(this) { message ->

            if (message.isNotEmpty()) {

                tvNewsStatus.text =
                    "Unable to load news."

                Toast.makeText(
                    this,
                    message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupBottomNavigation() {

        bottomNavigation.selectedItemId =
            R.id.nav_news

        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_weather -> {

                    val intent =
                        Intent(
                            this,
                            WeatherActivity::class.java
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

                    finish()

                    true
                }

                R.id.nav_news -> {
                    true
                }

                else -> false
            }
        }
    }
}