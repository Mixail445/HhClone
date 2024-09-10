package com.example.detaliSearch.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailSalaryDto(
    val full: String,
    val short: String? = null,
)
