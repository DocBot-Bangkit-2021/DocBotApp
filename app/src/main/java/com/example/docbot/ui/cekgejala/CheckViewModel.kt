package com.example.docbot.ui.cekgejala

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docbot.data.*
import com.example.docbot.ui.information.network.RetrofitInformation
import com.example.docbot.utils.DataDummy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CheckViewModel: ViewModel() {

    fun getPuskesmas(): List<PuskesmasEntity> = DataDummy.generateDummyPuskesmas()

    val listNews = MutableLiveData<ArrayList<InformationEntity>>()
    fun getNews(): LiveData<ArrayList<InformationEntity>> = listNews
    fun setNews(name: String){
        RetrofitInformation.create().getDisease().enqueue(object : Callback<List<ListResult>> {
            override fun onResponse(
                call: Call<List<ListResult>>,
                response: Response<List<ListResult>>
            ) {
                if (response.isSuccessful){
                    if (response.body().isNullOrEmpty()) return
                    else{
                        for (i in 0 until response.body()?.size!!){
                            val dsName = response.body()!![i].name
                            if (dsName.equals(name.trim(), ignoreCase = true)) {
                                listNews.postValue(response.body()!![i].article)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<ListResult>>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }
}