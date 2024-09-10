package com.example.detaliSearch.data.model

import com.example.detaliSearch.domain.model.DetailResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailResponseDto(
    val vacancies: List<DetailVacancyDto>,
    val offers: List<DetailOffersDto>,
) {
    fun mapToDomain() =
        DetailResponse(
            vacancy = vacancies.map { it.mapToDomain() },
        )
}
