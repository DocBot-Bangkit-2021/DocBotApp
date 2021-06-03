package com.example.docbot.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docbot.databinding.ActivityDashboardBinding
import com.example.docbot.ui.cekgejala.CheckActivity
import com.example.docbot.ui.cekgejala.CheckCameraActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DashboardViewModel::class.java]
        val ask = viewModel.getAsk()

        val dashboardAdapter = AskAdapter()
        dashboardAdapter.setAsk(ask)

        with(binding.rvAsk){
            layoutManager = LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = dashboardAdapter
        }

        val news = viewModel.getNews()
        val newsAdapter = NewsAdapter()
        newsAdapter.setNews(news)

        with(binding.rvNews){
            layoutManager = LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        val puskesmas = viewModel.getPuskesmas()
        val puskesmasAdapter = PuskesmasAdapter()
        puskesmasAdapter.setPuskesmas(puskesmas)

        with(binding.rvPuskesmas){
            layoutManager = LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = puskesmasAdapter
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, CheckCameraActivity::class.java))
        }
        binding.buttonCv19.setOnClickListener {
            startActivity(Intent(this, CheckActivity::class.java))
        }
    }
}