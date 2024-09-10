package com.example.search.domain

import com.example.data.FavoriteVacancyEntity

interface SearchLocalSource {
    suspend fun getAllFavorites(): List<FavoriteVacancyEntity>

    suspend fun addFavorite(vacancy: FavoriteVacancyEntity)

    suspend fun removeFavorite(vacancy: FavoriteVacancyEntity)

    suspend fun getFavoriteById(vacancyId: String): FavoriteVacancyEntity?
}
