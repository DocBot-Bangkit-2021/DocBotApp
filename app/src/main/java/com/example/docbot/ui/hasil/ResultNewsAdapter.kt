package com.example.docbot.ui.hasil

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.docbot.data.NewsEntity
import com.example.docbot.databinding.ItemArtikelBinding

class ResultNewsAdapter: RecyclerView.Adapter<ResultNewsAdapter.NewsViewHolder>() {

    private var listNews = ArrayList<NewsEntity>()

    fun setNews(news: List<NewsEntity>?){
        if (news == null) return
        this.listNews.clear()
        this.listNews.addAll(news)
    }

    class NewsViewHolder(private val binding: ItemArtikelBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news: NewsEntity){
            with(binding){
                tvTitle.text = news.title
                Glide.with(itemView.context)
                        .load(news.poster)
                        .into(imgBeside)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemArtikelBinding = ItemArtikelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(itemArtikelBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = listNews[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int = listNews.size
}