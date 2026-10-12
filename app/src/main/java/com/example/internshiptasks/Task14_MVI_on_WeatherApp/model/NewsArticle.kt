package com.example.internshiptasks.Task14_MVI_on_WeatherApp.model

import com.google.gson.annotations.SerializedName

data class NewsArticle(

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("url")
    val url: String,

    @SerializedName("image")
    val image: String?,

    @SerializedName("publishedAt")
    val publishedAt: String?,

    @SerializedName("source")
    val source: NewsSource?

)
