package com.example.docbot.ui.welcome

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.docbot.R
import com.example.docbot.databinding.ActivityWelcomeBinding
import com.example.docbot.ui.dashboard.DashboardActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var fromBottom: Animation
    private lateinit var fromTop: Animation
    private lateinit var fadeIn: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.statusBarColor = Color.TRANSPARENT

        if (getPref()) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        //init
        binding.footer.setBackgroundResource(R.drawable.footer)
        binding.welcomeLayout.setBackgroundResource(R.drawable.bg_welcome)
        binding.tvTitle.text = getString(R.string.intro_title_1)
        binding.description.text = getString(R.string.welcome_1)

        // Load Animations
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom)

        binding.footer.animation = fromBottom

        val dummyImage: List<Int> =
            listOf(R.drawable.welcome_1, R.drawable.welcome_2, R.drawable.welcome_3)

        val adapter = SectionPagerAdapter(this, dummyImage)
        binding.viewPager.adapter = adapter

        binding.tab.setupWithViewPager(binding.viewPager)

        val titleWelcome = intArrayOf(
            R.string.intro_title_1,
            R.string.intro_title_2,
            R.string.intro_title_3,
        )
        val descWelcome = intArrayOf(R.string.welcome_1, R.string.welcome_2, R.string.welcome_3)

        binding.btnNext.setOnClickListener {
            var position = binding.viewPager.currentItem
            if (position < dummyImage.size) {
                position++
                binding.viewPager.currentItem = position
            }
        }

        binding.btnDashboard.setOnClickListener {
            toDashboard()
        }

        binding.btnSkip.setOnClickListener {
            toDashboard()
        }

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                binding.tvTitle.text = getString(titleWelcome[position])
                binding.description.text = getString(descWelcome[position])
                if (position == dummyImage.size - 1) {
                    binding.btnLayout.visibility = View.GONE
                    binding.tab.visibility = View.GONE
                    binding.btnDashboard.visibility = View.VISIBLE
                } else {
                    binding.btnLayout.visibility = View.VISIBLE
                    binding.tab.visibility = View.VISIBLE
                    binding.btnDashboard.visibility = View.GONE
                }
//                Toast.makeText(this@WelcomeActivity, "$position", Toast.LENGTH_SHORT).show()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

    }

    private fun getPref(): Boolean {
        val sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE)
        return sharedPreferences.getBoolean(IS_WELCOME, false)
    }

    private fun toDashboard() {
        val sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(IS_WELCOME, true)
        editor.apply()

        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    companion object {
        const val SHARED_PREF = "sharedPref"
        const val IS_WELCOME = "is_welcome"
    }

}