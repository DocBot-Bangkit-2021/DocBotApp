package com.example.docbot.ui.information.network

import com.example.docbot.data.ListInformation
import retrofit2.Call
import retrofit2.http.GET

interface Service {
    @GET("v2/top-headlines?country=id&apiKey=22b645b7cb554e2cb89b3532aee31bd0")
    fun getAllInformation(): Call<ListInformation>
}