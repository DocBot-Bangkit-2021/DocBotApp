package com.example.docbot.ui.cekgejala

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.docbot.R
import com.example.docbot.databinding.ActivityCheckCameraBinding

class CheckCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAmbil.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }
    }
}