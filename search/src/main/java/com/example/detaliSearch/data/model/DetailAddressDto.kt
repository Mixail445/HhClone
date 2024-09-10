package com.example.detaliSearch.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailAddressDto(
    val town: String,
    val street: String,
    val house: String,
)
