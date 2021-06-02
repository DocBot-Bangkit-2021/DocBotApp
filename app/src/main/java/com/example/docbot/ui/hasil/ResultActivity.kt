package com.example.docbot.ui.hasil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.docbot.R
import com.example.docbot.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val image = intent.getStringExtra(EXTRA_IMAGE)
        val vit = intent.getStringExtra(EXTRA_VIT)

        binding.tvAnalisis.text = resources.getString(R.string.hasil_analisis, name, vit)

        Glide.with(this).load(image).into(binding.imageView2)
    }

    companion object{
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_VIT = "extra_vit"
    }
}