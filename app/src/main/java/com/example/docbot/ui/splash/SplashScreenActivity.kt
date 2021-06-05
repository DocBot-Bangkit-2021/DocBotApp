package com.example.docbot.ui.splash

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docbot.R
import com.example.docbot.databinding.ActivityDashboardBinding
import com.example.docbot.databinding.ActivitySplashScreenBinding
import com.example.docbot.ui.dashboard.DashboardActivity
import com.example.docbot.ui.home.MainActivity
import com.example.docbot.ui.welcome.WelcomeActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Transparant toolbar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.statusBarColor = Color.TRANSPARENT

        val fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        with(binding){
            binding.docbotLogo.startAnimation(fadein)
            binding.buble.startAnimation(fadein)
            binding.bubleSplash1.startAnimation(fadein)
            binding.bubleSplash2.startAnimation(fadein)
            binding.bubleSplash3.startAnimation(fadein)
            binding.bubleSplash4.startAnimation(fadein)
            binding.docbotLogo.startAnimation(fadein)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }, 4000)
    }

}