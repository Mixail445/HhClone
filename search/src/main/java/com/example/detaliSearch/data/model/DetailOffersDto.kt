package com.example.detaliSearch.data.model

import com.example.search.data.model.ButtonDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailOffersDto(
    val id: String? = null,
    val title: String,
    val link: String,
    val button: ButtonDto? = null,
)
