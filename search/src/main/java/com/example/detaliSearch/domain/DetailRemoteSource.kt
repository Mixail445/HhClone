package com.example.detaliSearch.domain

import com.example.detaliSearch.data.model.DetailResponseDto

interface DetailRemoteSource {
    suspend fun getVacancyRemote(id: String): DetailResponseDto
}
