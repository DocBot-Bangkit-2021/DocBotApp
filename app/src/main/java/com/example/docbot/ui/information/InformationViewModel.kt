package com.example.docbot.ui.information

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docbot.data.InformationEntity
import com.example.docbot.data.ListInformation
import com.example.docbot.ui.information.network.RetrofitInformation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformationViewModel: ViewModel() {

    val listInformation = MutableLiveData<ArrayList<InformationEntity>>()

    fun setNewsInformation(){
        RetrofitInformation.create().getCovidInfo().enqueue(object : Callback<ListInformation>{
            override fun onResponse(
                call: Call<ListInformation>,
                response: Response<ListInformation>
            ) {
                if (response.isSuccessful){
                    listInformation.postValue(response.body()?.article)
                }
            }

            override fun onFailure(call: Call<ListInformation>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getNewsInformation(): LiveData<ArrayList<InformationEntity>> = listInformation
}