package com.example.docbot.ui.information.network

import com.example.docbot.data.ListInformation
import com.example.docbot.data.ListPuskesmas
import com.example.docbot.data.ListResult
import com.example.docbot.data.PuskesmasEntity
import retrofit2.Call
import retrofit2.http.GET

interface Service {
    @GET("covidinfo")
    fun getCovidInfo(): Call<ListInformation>

    @GET("unit_kesehatan")
    fun getPuskesmas(): Call<List<ListPuskesmas>>

    @GET("disease_info")
    fun getDisease(): Call<List<ListResult>>
}