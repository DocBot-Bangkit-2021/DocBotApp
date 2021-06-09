package com.example.docbot.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.docbot.databinding.ItemAskBinding

class AskAdapter : RecyclerView.Adapter<AskAdapter.DashboardViewHolder>() {

    private var listAsk = ArrayList<String>()

    fun setAsk(ask: List<String>?){
        if (ask == null) return
        this.listAsk.clear()
        this.listAsk.addAll(ask)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AskAdapter.DashboardViewHolder {
        val itemAskBinding = ItemAskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DashboardViewHolder(itemAskBinding)
    }

    override fun onBindViewHolder(holder: AskAdapter.DashboardViewHolder, position: Int) {
        val ask = listAsk[position]
        holder.bind(ask)
    }

    override fun getItemCount(): Int = listAsk.size

    class DashboardViewHolder(private val binding: ItemAskBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(ask: String){
            with(binding){
                tvAsk.text = ask
                itemView.setOnClickListener {
                    Toast.makeText(itemView.context, "Anda dalam keadaan yang $ask", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}