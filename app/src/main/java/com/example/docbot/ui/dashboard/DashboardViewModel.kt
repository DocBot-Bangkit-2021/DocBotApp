package com.example.docbot.ui.dashboard

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

class DashboardViewModel: ViewModel() {


    fun getAsk() : List<String> = DataDummy.generateDummyAsk()

    val listPuskesmas = MutableLiveData<ArrayList<PuskesmasEntity>>()
    fun setPuskesmas(){
        RetrofitInformation.create().getPuskesmas().enqueue(object : Callback<List<ListPuskesmas>> {
            override fun onResponse(
                call: Call<List<ListPuskesmas>>,
                response: Response<List<ListPuskesmas>>
            ) {
                if (response.isSuccessful){
                    listPuskesmas.postValue(response.body()?.get(0)?.article)
                }
            }

            override fun onFailure(call: Call<List<ListPuskesmas>>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getPuskesmas(): LiveData<ArrayList<PuskesmasEntity>> = listPuskesmas
}