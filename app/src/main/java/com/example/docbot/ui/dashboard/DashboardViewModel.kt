package com.example.docbot.ui.dashboard

import androidx.lifecycle.ViewModel
import com.example.docbot.data.NewsEntity
import com.example.docbot.data.PuskesmasEntity
import com.example.docbot.utils.DataDummy

class DashboardViewModel: ViewModel() {
    fun getAsk() : List<String> = DataDummy.generateDummyAsk()

    fun getNews(): List<NewsEntity> = DataDummy.generateDummyNews()

    fun getPuskesmas(): List<PuskesmasEntity> = DataDummy.generateDummyPuskesmas()
}