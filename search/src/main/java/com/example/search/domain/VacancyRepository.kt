package com.example.search.domain

import com.example.common.utils.AppResult
import com.example.search.domain.model.SearchResponse
import com.example.search.domain.model.Vacancy

interface VacancyRepository {
    suspend fun getVacancy(): AppResult<SearchResponse, Throwable>

    suspend fun removeVacancyFromFavorites(vacancyId: String)

    suspend fun addVacancyToFavorites(vacancy: Vacancy)

    suspend fun getFavorites(): List<Vacancy>

    suspend fun isFavorite(vacancyId: String): Boolean

    suspend fun getFavoriteVacanciesCount(): Int
}
