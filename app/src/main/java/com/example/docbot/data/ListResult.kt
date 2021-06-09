package com.example.docbot.data

data class ListResult(
    val name: String,
    val icon: String,
    val id: Int,
    val article: ArrayList<InformationEntity>
)
