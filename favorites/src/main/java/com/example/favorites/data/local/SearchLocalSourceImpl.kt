package com.example.favorites.data.local

import com.example.common.common.DispatchersProvider
import com.example.favorites.domain.FavoriteLocalSource
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteLocalSourceImpl
    @Inject
    constructor(
        private val dao: FavoriteDao,
        private val dispatchersProvider: DispatchersProvider,
    ) : FavoriteLocalSource {
        override suspend fun getAllFavorites(): List<FavoriteVacancyEntity> =
            withContext(dispatchersProvider.io) {
                dao.getAllFavorites()
            }

        override suspend fun addFavorite(vacancy: FavoriteVacancyEntity) =
            withContext(dispatchersProvider.io) {
                dao.addFavorite(vacancy)
            }

        override suspend fun removeFavorite(vacancy: FavoriteVacancyEntity) =
            withContext(dispatchersProvider.io) {
                dao.removeFavorite(vacancy)
            }

        override suspend fun getFavoriteById(vacancyId: String): FavoriteVacancyEntity? =
            withContext(dispatchersProvider.io) {
                dao.getFavoriteById(vacancyId)
            }
    }
