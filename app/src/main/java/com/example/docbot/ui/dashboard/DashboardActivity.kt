package com.example.docbot.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docbot.R
import com.example.docbot.databinding.ActivityDashboardBinding
import com.example.docbot.ui.about.AboutActivity
import com.example.docbot.ui.cekgejala.CheckActivity
import com.example.docbot.ui.cekgejala.CheckCameraActivity
import com.example.docbot.ui.information.InformationActivity
import com.example.docbot.ui.information.InformationViewModel

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting Toolbar
        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.title = "DocBot"
        binding.toolbarMain.subtitle = "Health Symptom Check System"

        // Drawer
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,  binding.toolbarMain,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        //to set work change icon navigation, must be on below drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.docbot_logo)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DashboardViewModel::class.java]
        val ask = viewModel.getAsk()

        val dashboardAdapter = AskAdapter()
        dashboardAdapter.setAsk(ask)

        with(binding.contentDashboard.rvAsk){
            layoutManager = LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = dashboardAdapter
        }

        // news
        val newsAdapter = NewsAdapter()
        newsAdapter.notifyDataSetChanged()
        val newsViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[InformationViewModel::class.java]
        newsViewModel.setNewsInformation()
        newsViewModel.getNewsInformation().observe(this, {
            newsAdapter.setNews(it.subList(0, 3))
            newsAdapter.notifyDataSetChanged()
        })


        with(binding.contentDashboard.rvNews){
            layoutManager = LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        val puskesmasAdapter = PuskesmasAdapter()
        puskesmasAdapter.notifyDataSetChanged()
        viewModel.setPuskesmas()
        viewModel.getPuskesmas().observe(this, {
            puskesmasAdapter.setPuskesmas(it)
            puskesmasAdapter.notifyDataSetChanged()
        })

        with(binding.contentDashboard.rvPuskesmas){
            layoutManager = LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = puskesmasAdapter
        }

        binding.contentDashboard.btnCekGejalaUmum.setOnClickListener {
            val intent = Intent(this, CheckCameraActivity::class.java)
            intent.putExtra(CheckCameraActivity.EXTRA_ACT, "umum")
            intent.putExtra(CheckCameraActivity.EXTRA_TITLE_TOOLBAR, "Cek Gejala Umum")
            startActivity(intent)
        }
        binding.contentDashboard.btnBuah.setOnClickListener {
            val intent = Intent(this, CheckCameraActivity::class.java)
            intent.putExtra(CheckCameraActivity.EXTRA_ACT, "buah")
            intent.putExtra(CheckCameraActivity.EXTRA_TITLE_TOOLBAR, "Kandungan Buah")
            startActivity(intent)
        }
        binding.contentDashboard.buttonCv19.setOnClickListener {
            startActivity(Intent(this, CheckActivity::class.java))
        }

        binding.contentDashboard.btnToInfoActivity.setOnClickListener {
            startActivity(Intent(this, InformationActivity::class.java))
        }
    }
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                val intent = Intent(this@DashboardActivity, AboutActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}