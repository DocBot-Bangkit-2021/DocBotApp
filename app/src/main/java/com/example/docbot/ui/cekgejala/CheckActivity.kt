package com.example.docbot.ui.cekgejala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.docbot.R
import com.example.docbot.databinding.ActivityCheckBinding
import com.example.docbot.ui.loadingcek.LoadingCekActivity

class CheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCekLoading.setOnClickListener {
            startActivity(Intent(this, LoadingCekActivity::class.java))
        }
    }
}