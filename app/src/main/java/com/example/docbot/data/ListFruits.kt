package com.example.docbot.data

data class ListFruits(
        val name: String,
        val icon: String,
        val vitamin: String,
        val benefits: String,
        val id: Int,
        val article: ArrayList<InformationEntity>
)
