package com.example.docbot.ui.information.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInformation {

    fun create(): Service {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://asia-southeast2-light-maker-312601.cloudfunctions.net/")
            .build()
        return retrofit.create(Service::class.java)
    }
}