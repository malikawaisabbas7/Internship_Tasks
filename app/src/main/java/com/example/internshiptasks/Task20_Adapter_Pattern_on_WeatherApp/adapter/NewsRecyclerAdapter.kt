package com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.internshiptasks.R
import com.example.internshiptasks.Task20_Adapter_Pattern_on_WeatherApp.model.NewsDisplayData

class NewsRecyclerAdapter(
    private var articles: List<NewsDisplayData> = emptyList(),
    private val onArticleClick: (NewsDisplayData) -> Unit = {}
) : RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

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
            .inflate(R.layout.item_news_mvp_task13, parent, false)

        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        val article = articles[position]

        holder.tvNewsHeadline.text = article.title
        holder.tvNewsDescription.text = article.description
        holder.tvNewsSource.text =
            "${article.sourceName} • ${article.publishedAt}"

        Glide.with(holder.itemView.context)
            .load(article.imageUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(holder.ivNewsImage)

        holder.itemView.setOnClickListener {
            onArticleClick(article)
        }
    }

    override fun getItemCount(): Int = articles.size

    fun updateArticles(newArticles: List<NewsDisplayData>) {
        articles = newArticles
        notifyDataSetChanged()
    }
}