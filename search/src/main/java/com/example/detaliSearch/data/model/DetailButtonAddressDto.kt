package com.example.detaliSearch.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailButtonAddressDto(
    val text: String,
)
