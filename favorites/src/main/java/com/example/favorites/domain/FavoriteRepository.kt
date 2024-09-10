package com.example.favorites.domain

import com.example.favorites.domain.model.FavoriteVacancy

interface FavoriteRepository {
    suspend fun removeVacancyFromFavorites(vacancyId: String)

    suspend fun getFavorites(): List<FavoriteVacancy>

    suspend fun isFavorite(vacancyId: String): Boolean

    suspend fun getFavoriteVacanciesCount(): Int
}
