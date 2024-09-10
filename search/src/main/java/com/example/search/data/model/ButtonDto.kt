package com.example.search.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ButtonDto(
    val text: String,
)
