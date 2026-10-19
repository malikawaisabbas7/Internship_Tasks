
package com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.adapter

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
import com.example.internshiptasks.Task19_Factory_Pattern_on_WeatherApp.model.NewsArticle
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

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_news_item_task11,
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

        holder.tvNewsSource.text =
            "${news.source.name} • ${formatDate(news.publishedAt)}"

        Glide.with(holder.itemView.context)
            .load(news.image)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(holder.ivNewsImage)

        holder.itemView.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(news.url)
            )

            val chooser = Intent.createChooser(
                browserIntent,
                "Open news article with"
            )

            holder.itemView.context.startActivity(chooser)
        }
    }

    fun updateData(newArticles: List<NewsArticle>) {
        newsList.clear()
        newsList.addAll(newArticles)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    private fun formatDate(date: String): String {
        return try {
            val inputFormat = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                Locale.getDefault()
            ).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }

            val outputFormat = SimpleDateFormat(
                "dd MMM yyyy",
                Locale.getDefault()
            )

            val parsedDate = inputFormat.parse(date)

            if (parsedDate != null) {
                outputFormat.format(parsedDate)
            } else {
                date
            }
        } catch (e: Exception) {
            date
        }
    }
}
