package com.example.search.domain

import com.example.search.data.model.SearchResponseDto

interface SearchRemoteSource {
    suspend fun getVacancyRemote(): SearchResponseDto
}
