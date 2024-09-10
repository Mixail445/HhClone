package com.example.search.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SalaryDto(
    val full: String,
    val short: String? = null,
)
