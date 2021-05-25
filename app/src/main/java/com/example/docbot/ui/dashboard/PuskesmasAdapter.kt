package com.example.docbot.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.docbot.data.PuskesmasEntity
import com.example.docbot.databinding.ItemPuskesmasBinding

class PuskesmasAdapter: RecyclerView.Adapter<PuskesmasAdapter.ViewHolder>() {

    private var listPuskesmas = ArrayList<PuskesmasEntity>()

    fun setPuskesmas(puskesmas: List<PuskesmasEntity>?){
        if (puskesmas == null) return
        this.listPuskesmas.clear()
        this.listPuskesmas.addAll(puskesmas)
    }

    class ViewHolder(private val binding:ItemPuskesmasBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: PuskesmasEntity){
            with(binding){
                tvTitle.text = data.name
                tvAddress.text = data.address
                tvPhone.text = data.phone
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemPuskesmasBinding = ItemPuskesmasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemPuskesmasBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listPuskesmas[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listPuskesmas.size
}