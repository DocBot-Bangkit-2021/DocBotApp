package com.example.docbot.ui.hasil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docbot.R
import com.example.docbot.databinding.ActivityResultCvBinding
import com.example.docbot.ui.cekgejala.CheckViewModel
import com.example.docbot.ui.dashboard.PuskesmasAdapter

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
        val artikel = viewModel.getNews()
        val newsAdapter = ResultNewsAdapter()
        newsAdapter.setNews(artikel.take(3))
        with(binding.rvNewsRs){
            layoutManager = LinearLayoutManager(this@ResultCvActivity)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        //puskesmas
        val puskesmas = viewModel.getPuskesmas()
        val puskesmasAdapter = PuskesmasAdapter()
        puskesmasAdapter.setPuskesmas(puskesmas)
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