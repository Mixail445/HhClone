package com.example.favorites.data

import com.example.favorites.data.local.mapToDomain
import com.example.favorites.domain.FavoriteLocalSource
import com.example.favorites.domain.FavoriteRepository
import com.example.favorites.domain.model.FavoriteVacancy
import javax.inject.Inject

class FavoriteRepositoryImpl
    @Inject
    constructor(
        private val localSource: FavoriteLocalSource,
    ) : FavoriteRepository {
        override suspend fun removeVacancyFromFavorites(vacancyId: String) {
            val favorite = localSource.getFavoriteById(vacancyId)
            favorite?.let { localSource.removeFavorite(it) }
        }

        override suspend fun getFavorites(): List<FavoriteVacancy> = localSource.getAllFavorites().map { it.mapToDomain() }

        override suspend fun isFavorite(vacancyId: String): Boolean = localSource.getFavoriteById(vacancyId) != null

        override suspend fun getFavoriteVacanciesCount(): Int = localSource.getAllFavorites().size
    }
