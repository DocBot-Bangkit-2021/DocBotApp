package com.example.docbot.ui.hasil

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.docbot.data.InformationEntity
import com.example.docbot.databinding.ItemArtikelBinding
import com.example.docbot.ui.information.detail.DetailInformationActivity

class ResultNewsAdapter: RecyclerView.Adapter<ResultNewsAdapter.NewsViewHolder>() {

    private var listNews = ArrayList<InformationEntity>()

    fun setNews(news: List<InformationEntity>?){
        if (news == null) return
        this.listNews.clear()
        this.listNews.addAll(news)
    }

    class NewsViewHolder(private val binding: ItemArtikelBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news: InformationEntity){
            with(binding){
                tvTitle.text = news.name
                Glide.with(itemView.context)
                        .load(news.image)
                        .into(imgBeside)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailInformationActivity::class.java)
                intent.putExtra(DetailInformationActivity.EXTRA_URL, news.link)
                intent.putExtra(DetailInformationActivity.EXTRA_TITLE, news.name)
                itemView.context.startActivity(intent)
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