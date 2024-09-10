package com.example.search.data.local

import com.example.common.common.DispatchersProvider
import com.example.search.domain.SearchLocalSource
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchLocalSourceImpl
    @Inject
    constructor(
        private val dao: SearchDao,
        private val dispatchersProvider: DispatchersProvider,
    ) : SearchLocalSource {
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
