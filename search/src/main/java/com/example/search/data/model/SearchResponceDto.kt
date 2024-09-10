package com.example.search.data.model

import com.example.search.domain.model.SearchResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponseDto(
    val offers: List<OffersDto>,
    val vacancies: List<VacancyDto>,
) {
    fun mapToDomain() =
        SearchResponse(
            offers = offers.map { it.mapToDomain() },
            vacancies = vacancies.map { it.mapToDomain() },
        )
}
