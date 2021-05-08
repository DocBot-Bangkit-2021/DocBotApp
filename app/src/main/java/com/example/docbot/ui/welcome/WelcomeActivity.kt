package com.example.docbot.ui.welcome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.docbot.R
import com.example.docbot.databinding.ActivityWelcomeBinding
import com.google.android.material.tabs.TabLayout

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        binding.footer.setBackgroundResource(R.drawable.footer)
        binding.welcomeLayout.setBackgroundResource(R.drawable.bg_welcome)
        binding.tvTitle.text = getString(R.string.intro_title_1)
        binding.description.text = getString(R.string.welcome_1)

        var dummyImage : List<Int> = listOf(R.drawable.welcome_1, R.drawable.welcome_2, R.drawable.welcome_3)

        val adapter = SectionPagerAdapter(this, dummyImage)
        binding.viewPager.adapter = adapter

        binding.tab.setupWithViewPager(binding.viewPager)

        val titleWelcome = intArrayOf (
                R.string.intro_title_1,
                R.string.intro_title_2,
                R.string.intro_title_3
        )
        val descWelcome = intArrayOf(R.string.welcome_1, R.string.welcome_2, R.string.welcome_3)

        binding.btnNext.setOnClickListener {
            var position = binding.viewPager.currentItem
            if(position < dummyImage.size){
                position ++
                binding.viewPager.currentItem = position
            }
            if (position == dummyImage.size - 1){
            }
        }

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                binding.tvTitle.text = getString(titleWelcome[position])
                binding.description.text  = getString(descWelcome[position])
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

    }
}