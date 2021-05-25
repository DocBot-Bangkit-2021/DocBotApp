package com.example.docbot.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.docbot.data.NewsEntity
import com.example.docbot.databinding.ItemNewsBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var listNews = ArrayList<NewsEntity>()

    fun setNews(news: List<NewsEntity>?){
        if (news == null) return
        this.listNews.clear()
        this.listNews.addAll(news)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemNewsBinding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(itemNewsBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = listNews[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int = listNews.size

    class NewsViewHolder(private val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news: NewsEntity){
            with(binding){
                tvTitleNews.text = news.title
                tvDescriptionNews.text = news.description

                Glide.with(itemView.context)
                    .load(news.poster)
                    .into(imgNews)
            }
        }
    }

}