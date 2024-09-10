package com.example.detaliSearch.domain

import com.example.common.utils.AppResult
import com.example.detaliSearch.domain.model.DetailVacancy

interface DetailRepository {
    suspend fun getVacancy(id: String): AppResult<DetailVacancy?, Throwable>
}
