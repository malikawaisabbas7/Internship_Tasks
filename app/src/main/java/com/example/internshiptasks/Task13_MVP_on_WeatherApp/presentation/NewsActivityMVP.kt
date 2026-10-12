
package com.example.internshiptasks.Task13_MVP_on_WeatherApp.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.internshiptasks.R
import com.example.internshiptasks.Task13_MVP_on_WeatherApp.adapter.NewsAdapter
import com.example.internshiptasks.Task13_MVP_on_WeatherApp.contract.NewsContract
import com.example.internshiptasks.Task13_MVP_on_WeatherApp.model.NewsArticle
import com.example.internshiptasks.Task13_MVP_on_WeatherApp.model.NewsResponse
import com.example.internshiptasks.Task13_MVP_on_WeatherApp.presenter.NewsPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivityMVP :
    AppCompatActivity(),
    NewsContract.View {

    private lateinit var tvNewsLocation: TextView
    private lateinit var tvNewsStatus: TextView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var recyclerViewNews: RecyclerView

    private lateinit var newsAdapter: NewsAdapter
    private val newsList = ArrayList<NewsArticle>()

    private lateinit var newsPresenter: NewsPresenter

    private var cityName: String = "Unknown City"
    private var countryName: String = "Unknown Country"

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_mvp_task13)

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

        newsPresenter = NewsPresenter(this)

        setupBottomNavigation()

        newsPresenter.getNews(cityName)
    }

    override fun showNews(newsResponse: NewsResponse) {

        newsList.clear()
        newsList.addAll(newsResponse.articles)

        newsAdapter.notifyDataSetChanged()

        tvNewsStatus.text = if (newsList.isEmpty()) {
            "No local news found."
        } else {
            "${newsList.size} news articles found."
        }
    }

    override fun showLoading() {
        tvNewsStatus.text = "Loading news..."
    }

    override fun showError(message: String) {

        tvNewsStatus.text = "Unable to load news."

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun setupBottomNavigation() {

        bottomNavigation.selectedItemId = R.id.nav_news

        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_weather -> {

                    val intent = Intent(
                        this,
                        WeatherActivityMVP::class.java
                    )

                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)

                    startActivity(intent)
                    finish()
                    true
                }

                R.id.nav_news -> true

                else -> false
            }
        }
    }
}