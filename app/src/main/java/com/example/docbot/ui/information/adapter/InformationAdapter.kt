package com.example.docbot.ui.information.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.docbot.data.InformationEntity
import com.example.docbot.databinding.ItemInformationBinding

class InformationAdapter : RecyclerView.Adapter<InformationAdapter.InformationViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listInformation = ArrayList<InformationEntity>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setListInformation(items: ArrayList<InformationEntity>){
        listInformation.clear()
        listInformation.addAll(items)
        notifyDataSetChanged()
    }

    inner class InformationViewHolder(private val binding: ItemInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(items: InformationEntity) {
            binding.apply {
                tvTitleInformation.text = items.name
                Glide.with(itemView).load(items.image).centerCrop().into(ivImageInformation)
            }
            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        val view = ItemInformationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InformationViewHolder(view)
    }

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        holder.bind(listInformation[position])
    }

    override fun getItemCount(): Int = listInformation.size

    interface OnItemClickCallback {
        fun onItemClicked(data: InformationEntity)
    }
}