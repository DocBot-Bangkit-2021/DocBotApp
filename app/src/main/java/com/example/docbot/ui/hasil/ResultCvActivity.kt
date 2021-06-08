package com.example.docbot.ui.hasil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docbot.R
import com.example.docbot.databinding.ActivityResultCvBinding
import com.example.docbot.ui.cekgejala.CheckViewModel
import com.example.docbot.ui.dashboard.NewsAdapter
import com.example.docbot.ui.dashboard.PuskesmasAdapter
import com.example.docbot.ui.information.InformationViewModel

class ResultCvActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultCvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultCvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[CheckViewModel::class.java]
        val result = intent.getStringExtra(EXTRA_DATA)

        if (result == "positif"){
            binding.textView17.text = resources.getString(R.string.hasil_analisis_2, "Covid 19")
            binding.textView18.text = resources.getString(R.string.pengananan, "isolasi mandiri")
        }else{
            binding.textView17.text = "DocBot menganalisis kamu Sehat!"
            binding.textView18.text = "Tidak ada yang perlu kamu khawatirkan"
        }

        //artikel
        val newsAdapter = ResultNewsAdapter()
        newsAdapter.notifyDataSetChanged()
        val newsViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[InformationViewModel::class.java]
        newsViewModel.setNewsInformation()
        newsViewModel.getNewsInformation().observe(this, {
            newsAdapter.setNews(it.subList(2, 4))
            newsAdapter.notifyDataSetChanged()
        })
        with(binding.rvNewsRs){
            layoutManager = LinearLayoutManager(this@ResultCvActivity)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        //puskesmas
        val puskesmasAdapter = PuskesmasAdapter()
        puskesmasAdapter.notifyDataSetChanged()
        viewModel.setPuskesmas()
        viewModel.getPuskesmas().observe(this, {
            puskesmasAdapter.setPuskesmas(it)
            puskesmasAdapter.notifyDataSetChanged()
        })
        with(binding.rvPuskesmasRs){
            layoutManager = LinearLayoutManager(this@ResultCvActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = puskesmasAdapter
        }

    }

    companion object{
        const val EXTRA_DATA = "extra_data"
    }
}