package com.example.internshiptasks.Task9_Weather_and_News_app

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task9_Weather_and_News_app.News.NewsAdapter
import com.example.internshiptasks.Task9_Weather_and_News_app.News.NewsApi
import com.example.internshiptasks.Task9_Weather_and_News_app.News.NewsModel
import com.example.internshiptasks.Task9_Weather_and_News_app.News.NewsResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsActivity : AppCompatActivity() {

    private lateinit var tvNewsLocation: TextView
    private lateinit var tvNewsStatus: TextView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recyclerViewNews: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val newsList = ArrayList<NewsModel>()
    private var cityName: String = ""
    private var countryName: String = ""

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private val GNEWS_API_KEY= "6fa25f5376f51c272aac8fed019c8e77"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_news_task9)

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

        val retrofit = Retrofit.Builder()
            .baseUrl("https://gnews.io/api/v4/")
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

        val newsApi =
            retrofit.create(NewsApi::class.java)

        val call = newsApi.getNews(
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
                        this@NewsActivity,
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
                    this@NewsActivity,
                    "Internet or API error.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
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