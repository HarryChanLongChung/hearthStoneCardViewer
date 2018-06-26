package com.demo.hearthstone.datamodels

import com.squareup.moshi.Json

class Models {
    data class MpApiJsonResponse(
            @Json(name = "Basic") val Cards: List<Card> = listOf()
    )

    data class Card(
            val cardId: String = "",
            val name: String = "",
            val img: String = "",
            val type: String = "",
            val playerClass: String = ""
    )
}