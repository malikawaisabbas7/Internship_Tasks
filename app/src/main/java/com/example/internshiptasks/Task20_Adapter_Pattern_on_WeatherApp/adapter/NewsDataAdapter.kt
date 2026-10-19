package com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.adapter

import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.model.NewsDisplayData
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.model.NewsResponse

class NewsDataAdapter(
    private val response: NewsResponse
) : NewsDisplayTarget {

    override fun getDisplayArticles(): List<NewsDisplayData> {
        return response.articles.map { article ->
            NewsDisplayData(
                title = article.title,
                description = article.description
                    ?: article.content
                    ?: "No description available",
                imageUrl = article.image,
                articleUrl = article.url,
                sourceName = article.source.name,
                publishedAt = article.publishedAt
            )
        }
    }
}
