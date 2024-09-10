package com.example.search.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressDto(
    val town: String,
    val street: String,
    val house: String,
)
