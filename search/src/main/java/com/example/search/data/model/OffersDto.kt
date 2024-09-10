package com.example.search.data.model

import com.example.search.domain.model.Offers
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OffersDto(
    val id: String? = null,
    val title: String,
    val link: String,
    val button: ButtonDto? = null,
) {
    fun mapToDomain() =
        Offers(
            id = id,
            title = title,
            button = button?.text,
            url = link,
        )
}
