package com.example.docbot.ui.information.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.docbot.data.InformationEntity
import com.example.docbot.databinding.ItemArticleBinding

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listArticle = ArrayList<InformationEntity>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setListArticle(items: ArrayList<InformationEntity>){
        listArticle.clear()
        listArticle.addAll(items)
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: InformationEntity) {
            binding.apply {
                titleArticle.text = items.name
                Glide.with(itemView).load(items.image).centerCrop().into(ivArticle)
            }

            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(listArticle[position])
    }

    override fun getItemCount(): Int = listArticle.size

    interface OnItemClickCallback {
        fun onItemClicked(data: InformationEntity)
    }
}