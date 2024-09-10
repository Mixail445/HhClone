package com.example.search.data

import com.example.common.utils.AppResult
import com.example.common.utils.ResultWrapper
import com.example.search.data.local.mapToDomain
import com.example.search.domain.SearchLocalSource
import com.example.search.domain.SearchRemoteSource
import com.example.search.domain.VacancyRepository
import com.example.search.domain.model.SearchResponse
import com.example.search.domain.model.Vacancy
import javax.inject.Inject

class SearchRepositoryImpl
    @Inject
    constructor(
        private val remoteSource: SearchRemoteSource,
        private val localSource: SearchLocalSource,
        private val wrapper: ResultWrapper,
    ) : VacancyRepository {
        override suspend fun getVacancy(): AppResult<SearchResponse, Throwable> =
            wrapper.wrap {
                remoteSource.getVacancyRemote().mapToDomain()
            }

        override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
            localSource.addFavorite(vacancy.mapToEmpty())
        }

        override suspend fun removeVacancyFromFavorites(vacancyId: String) {
            val favorite = localSource.getFavoriteById(vacancyId)
            favorite?.let { localSource.removeFavorite(it) }
        }

        override suspend fun getFavorites(): List<Vacancy> = localSource.getAllFavorites().map { it.mapToDomain() }

        override suspend fun isFavorite(vacancyId: String): Boolean = localSource.getFavoriteById(vacancyId) != null

        override suspend fun getFavoriteVacanciesCount(): Int = localSource.getAllFavorites().size
    }
