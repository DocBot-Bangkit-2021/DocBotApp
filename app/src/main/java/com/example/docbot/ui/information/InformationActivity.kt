package com.example.docbot.ui.information

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.docbot.data.InformationEntity
import com.example.docbot.databinding.ActivityInformationBinding
import com.example.docbot.ui.information.adapter.ArticleAdapter
import com.example.docbot.ui.information.adapter.InformationAdapter
import com.example.docbot.ui.information.detail.DetailInformationActivity

class InformationActivity : AppCompatActivity() {

    private lateinit var activityInformationBinding: ActivityInformationBinding
    private lateinit var informationAdapter: InformationAdapter
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var informationViewModel: InformationViewModel

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityInformationBinding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(activityInformationBinding.root)

        supportActionBar?.title = "Information"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        informationAdapter = InformationAdapter()
        informationAdapter.notifyDataSetChanged()

        articleAdapter = ArticleAdapter()
        articleAdapter.notifyDataSetChanged()

        informationViewModel = ViewModelProvider(this).get(InformationViewModel::class.java)

        informationAdapter.setOnItemClickCallback(object : InformationAdapter.OnItemClickCallback{
            override fun onItemClicked(data: InformationEntity) {
                val intent = Intent(this@InformationActivity, DetailInformationActivity::class.java)
                intent.putExtra(DetailInformationActivity.EXTRA_URL, data.desc)
                intent.putExtra(DetailInformationActivity.EXTRA_TITLE, data.name)
                startActivity(intent)
            }

        })

        articleAdapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback{
            override fun onItemClicked(data: InformationEntity) {
                val intent = Intent(this@InformationActivity, DetailInformationActivity::class.java)
                intent.putExtra(DetailInformationActivity.EXTRA_URL, data.desc)
                intent.putExtra(DetailInformationActivity.EXTRA_TITLE, data.name)
                startActivity(intent)
            }
        })

        activityInformationBinding.rvInfoHorizontal.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        activityInformationBinding.rvInfoHorizontal.setHasFixedSize(true)
        activityInformationBinding.rvInfoHorizontal.adapter = informationAdapter

        activityInformationBinding.rvArticleVertical.layoutManager = LinearLayoutManager(this)
        activityInformationBinding.rvArticleVertical.setHasFixedSize(true)
        activityInformationBinding.rvArticleVertical.adapter = articleAdapter

        informationViewModel.setNewsInformation()
        informationViewModel.getNewsInformation().observe(this, {
            if (it != null){
                informationAdapter.setListInformation(it)
                articleAdapter.setListArticle(it)
            }
        })

    }
}