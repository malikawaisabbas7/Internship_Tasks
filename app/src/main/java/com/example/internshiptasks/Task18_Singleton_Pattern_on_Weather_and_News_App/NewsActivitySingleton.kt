package com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.adapter.NewsAdapter
import com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.api.RetrofitClient
import com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.model.NewsArticle
import com.example.internshiptasks.Task18_Singleton_Pattern_on_Weather_and_News_App.model.NewsResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivitySingleton : AppCompatActivity() {

    private lateinit var tvNewsLocation: TextView
    private lateinit var tvNewsStatus: TextView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recyclerViewNews: RecyclerView

    private lateinit var newsAdapter: NewsAdapter
    private val newsList = ArrayList<NewsArticle>()

    private var cityName: String = "Unknown City"
    private var countryName: String = "Unknown Country"

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private val GNEWS_API_KEY = "6fa25f5376f51c272aac8fed019c8e77"

    companion object {
        const val EXTRA_CITY_NAME = "extra_city_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_news_singleton_task18
        )

        tvNewsLocation = findViewById(R.id.tvNewsLocation)
        tvNewsStatus = findViewById(R.id.tvNewsStatus)
        bottomNavigation = findViewById(R.id.bottomNavigation)
        recyclerViewNews = findViewById(R.id.recyclerViewNews)

        cityName = intent.getStringExtra(EXTRA_CITY_NAME)
            ?: intent.getStringExtra("cityName")
                    ?: "Unknown City"

        countryName = intent.getStringExtra("countryName")
            ?: "Unknown Country"

        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)

        tvNewsLocation.text = "📍 $cityName, $countryName"

        recyclerViewNews.layoutManager =
            LinearLayoutManager(this)

        newsAdapter = NewsAdapter(newsList)
        recyclerViewNews.adapter = newsAdapter

        getNews()

        setupBottomNavigation()
    }

    private fun getNews() {

        tvNewsStatus.text = "Loading news..."

        if (GNEWS_API_KEY == "YOUR_GNEWS_API_KEY") {
            tvNewsStatus.text =
                "Please configure your GNews API key."

            return
        }

         val call = RetrofitClient.newsApi.getNews(
            query = cityName,
            language = "en",
            country = "pk",
            maxArticles = 10,
            sortBy = "publishedAt",
            apiKey = GNEWS_API_KEY
        )

        call.enqueue(object : Callback<NewsResponse> {

            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {

                if (response.isSuccessful) {

                    val newsResponse = response.body()

                    if (newsResponse != null) {

                        newsList.clear()
                        newsList.addAll(newsResponse.articles)

                        newsAdapter.notifyDataSetChanged()

                        if (newsList.isEmpty()) {
                            tvNewsStatus.text =
                                "No local news found for $cityName."
                        } else {
                            tvNewsStatus.text =
                                "${newsList.size} news articles found."
                        }

                    } else {
                        tvNewsStatus.text =
                            "No news data received."
                    }

                } else {

                    tvNewsStatus.text =
                        "Unable to load news."

                    val errorMessage =
                        response.errorBody()?.string()

                    Toast.makeText(
                        this@NewsActivitySingleton,
                        "News API error: ${response.code()}\n$errorMessage",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(
                call: Call<NewsResponse>,
                t: Throwable
            ) {

                tvNewsStatus.text =
                    "Unable to connect to news service."

                Toast.makeText(
                    this@NewsActivitySingleton,
                    "Internet or API error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setupBottomNavigation() {

        bottomNavigation.selectedItemId = R.id.nav_news

        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_weather -> {

                    val intent = Intent(
                        this,
                        WeatherActivitySingleton::class.java
                    )

                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)

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