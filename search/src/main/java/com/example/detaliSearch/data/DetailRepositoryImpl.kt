package com.example.detaliSearch.data

import com.example.common.utils.AppResult
import com.example.common.utils.ResultWrapper
import com.example.detaliSearch.domain.DetailRemoteSource
import com.example.detaliSearch.domain.DetailRepository
import com.example.detaliSearch.domain.model.DetailVacancy
import javax.inject.Inject

class DetailRepositoryImpl
    @Inject
    constructor(
        private val remoteSource: DetailRemoteSource,
        private val wrapper: ResultWrapper,
    ) : DetailRepository {
        override suspend fun getVacancy(id: String): AppResult<DetailVacancy?, Throwable> =
            wrapper.wrap {
                remoteSource
                    .getVacancyRemote(id)
                    .vacancies
                    .find { it.id == id }
                    ?.mapToDomain()
            }
    }
