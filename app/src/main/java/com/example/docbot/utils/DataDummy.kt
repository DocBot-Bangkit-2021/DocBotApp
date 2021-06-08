package com.example.docbot.utils

import com.example.docbot.R
import com.example.docbot.data.NewsEntity
import com.example.docbot.data.PuskesmasEntity

object DataDummy {
    fun generateDummyAsk(): List<String>{
        val ask = ArrayList<String>()

        ask.add("Tanya 1")
        ask.add("Tanya 2")
        ask.add("Tanya 3")
        ask.add("Tanya 4")
        ask.add("Tanya 5")

        return ask
    }

    fun generateDummyNews(): List<NewsEntity>{
        val news = ArrayList<NewsEntity>()

        news.add(
            NewsEntity(R.drawable.news1,
                "Mengapa Kulit menjadi dehidrasi ?",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
        )
        news.add(
            NewsEntity(R.drawable.news1,
                "Mengapa Kulit menjadi dehidrasi ?",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
        )
        news.add(
            NewsEntity(R.drawable.news1,
                "Mengapa Kulit menjadi dehidrasi ?",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
        )
        news.add(
            NewsEntity(R.drawable.news1,
                "Mengapa Kulit menjadi dehidrasi ?",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
        )
        news.add(
            NewsEntity(R.drawable.news1,
                "Mengapa Kulit menjadi dehidrasi ?",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
        )

        return news
    }

    fun generateDummyPuskesmas(): List<PuskesmasEntity>{
        val puskesmas = ArrayList<PuskesmasEntity>()

        puskesmas.add(
            PuskesmasEntity(1,"Puskesmas Jati Negara",
            "","Jl. dimana mana hatiku suka ada puskesmas",
            "08123456789")
        )
        puskesmas.add(
            PuskesmasEntity(2,"Puskesmas Jati Negara",
                "","Jl. dimana mana hatiku suka ada puskesmas",
                "08123456789")
        )
        puskesmas.add(
            PuskesmasEntity(3,"Puskesmas Jati Negara",
                "", "Jl. dimana mana hatiku suka ada puskesmas",
                "08123456789")
        )
        return puskesmas
    }
}