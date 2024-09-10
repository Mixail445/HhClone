package com.example.search.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExperienceDto(
    val previewText: String,
    val text: String,
)
