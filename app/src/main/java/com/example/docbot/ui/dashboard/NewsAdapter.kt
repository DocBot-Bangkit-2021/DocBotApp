package com.example.docbot.ui.dashboard

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.docbot.data.InformationEntity
import com.example.docbot.databinding.ItemNewsBinding
import com.example.docbot.ui.information.detail.DetailInformationActivity

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var listNews = ArrayList<InformationEntity>()

    fun setNews(news: MutableList<InformationEntity>?){
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
        fun bind(news: InformationEntity){
            with(binding){
                tvTitleNews.text = news.name
                tvDescriptionNews.text = news.desc

                Glide.with(itemView.context)
                    .load(news.image)
                    .into(imgNews)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailInformationActivity::class.java)
                    intent.putExtra(DetailInformationActivity.EXTRA_URL, news.link)
                    intent.putExtra(DetailInformationActivity.EXTRA_TITLE, news.name)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}