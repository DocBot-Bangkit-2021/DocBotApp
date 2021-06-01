package com.example.docbot.ui.cekgejala

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.docbot.R
import com.example.docbot.databinding.ActivityCheckCameraBinding

class CheckCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckCameraBinding

    companion object {
        const val CODE_CAMERA = 1
        const val CODE_GALLERY = 2
    }

    private var status_photo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAmbil.setOnClickListener {
            startActivityForResult(Intent(this, CameraActivity::class.java), CODE_CAMERA)
        }
        binding.btnPilih.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, CODE_GALLERY)
        }
        binding.btnCek.setOnClickListener {
            if (status_photo){

            }
            else Toast.makeText(this, "Ambil gambar dulu", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CODE_CAMERA){
            if(resultCode == Activity.RESULT_OK){
                val uri = data?.getStringExtra(CameraActivity.CEK_URI)
                Toast.makeText(this, "$uri", Toast.LENGTH_SHORT).show()
                Glide.with(this)
                        .load(uri)
                        .into(binding.imageView2)
                binding.imageView2.rotation = 90F
                status_photo = true
            }
        }
        else if (requestCode == CODE_GALLERY && resultCode == Activity.RESULT_OK){
            Glide.with(this).load(data?.data).into(binding.imageView2)
            status_photo = true
        }
    }
}