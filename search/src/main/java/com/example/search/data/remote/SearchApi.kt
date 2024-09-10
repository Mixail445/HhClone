package com.example.search.data.remote

import com.example.search.data.model.SearchResponseDto

interface SearchApi {
    suspend fun getVacancy(): SearchResponseDto
}
