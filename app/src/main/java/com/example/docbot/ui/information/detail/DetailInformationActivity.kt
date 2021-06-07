package com.example.docbot.ui.information.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.docbot.R
import kotlinx.android.synthetic.main.activity_detail_information.*

class DetailInformationActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_URL = "extra_url"
        const val EXTRA_TITLE = "extra_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_information)

        val title = intent.getStringExtra(EXTRA_TITLE)

        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        webViewSetup()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup() {
        val url = intent.getStringExtra(EXTRA_URL)
        wb_detail_information.webViewClient = WebViewClient()

        wb_detail_information.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
        }
    }

    override fun onBackPressed() {
        if (wb_detail_information.canGoBack()) wb_detail_information.goBack() else super.onBackPressed()
    }
}