package com.example.internshiptasks.Task14_MVI_on_WeatherApp.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.internshiptasks.R
import com.example.internshiptasks.Task14_MVI_on_WeatherApp.model.NewsArticle
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class NewsAdapter(
    private val newsList: ArrayList<NewsArticle> = arrayListOf()
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val ivNewsImage: ImageView =
            itemView.findViewById(R.id.ivNewsImage)

        val tvNewsHeadline: TextView =
            itemView.findViewById(R.id.tvNewsHeadline)

        val tvNewsDescription: TextView =
            itemView.findViewById(R.id.tvNewsDescription)

        val tvNewsSource: TextView =
            itemView.findViewById(R.id.tvNewsSource)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_news_mvi_task14,
                parent,
                false
            )

        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {

        val news = newsList[position]

        holder.tvNewsHeadline.text = news.title

        holder.tvNewsDescription.text =
            news.description ?: "No description available"

        val sourceName = news.source?.name ?: "Unknown Source"
        val publishedDate = formatDate(news.publishedAt)

        holder.tvNewsSource.text =
            "$sourceName • $publishedDate"

        Glide.with(holder.itemView.context)
            .load(news.image)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery)
            .into(holder.ivNewsImage)

        holder.itemView.setOnClickListener {

            val articleUrl = news.url

            if (articleUrl.isNotBlank()) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(articleUrl)
                )

                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun updateNews(newArticles: List<NewsArticle>) {
        newsList.clear()
        newsList.addAll(newArticles)
        notifyDataSetChanged()
    }

    private fun formatDate(date: String?): String {

        if (date.isNullOrBlank()) {
            return "Date unavailable"
        }

        return try {
            val inputFormat = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                Locale.US
            )

            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val outputFormat = SimpleDateFormat(
                "dd MMM yyyy",
                Locale.getDefault()
            )

            val parsedDate = inputFormat.parse(date)
            parsedDate?.let { outputFormat.format(it) }
                ?: "Date unavailable"

        } catch (e: Exception) {
            date
        }
    }
}
