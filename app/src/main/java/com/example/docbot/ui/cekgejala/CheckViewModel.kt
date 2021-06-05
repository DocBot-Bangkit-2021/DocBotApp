package com.example.docbot.ui.cekgejala

import androidx.lifecycle.ViewModel
import com.example.docbot.data.NewsEntity
import com.example.docbot.data.PuskesmasEntity
import com.example.docbot.utils.DataDummy

class CheckViewModel: ViewModel() {
    fun getNews(): List<NewsEntity> = DataDummy.generateDummyNews()

    fun getPuskesmas(): List<PuskesmasEntity> = DataDummy.generateDummyPuskesmas()
}