package com.example.internshiptasks.Task9_Weather_and_News_app.News

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
import java.text.SimpleDateFormat
import java.util.Locale
class NewsAdapter(


    private val newsList: ArrayList<NewsModel>
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
                R.layout.activity_item_news_task9,
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

        holder.tvNewsHeadline.text =
            news.title

        holder.tvNewsDescription.text =
            news.description

        holder.tvNewsSource.text =
            "${news.source.name} • ${formatDate(news.publishedAt)}"

        Glide.with(holder.itemView.context)
            .load(news.image)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.ivNewsImage)

        holder.itemView.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(news.url)
            )

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    private fun formatDate(date: String): String {

        return try {

            val inputFormat =
                SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'",
                    Locale.getDefault()
                )

            val outputFormat =
                SimpleDateFormat(
                    "dd MMM yyyy",
                    Locale.getDefault()
                )

            val parsedDate =
                inputFormat.parse(date)

            outputFormat.format(parsedDate!!)

        } catch (e: Exception) {

            date
        }
    }
}