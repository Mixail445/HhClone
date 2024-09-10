package com.example.favorites.data.model

import com.example.favorites.domain.model.FavoriteResponse

data class FavoriteResponseDto(
    val offers: List<FavoriteOffersEntity>,
    val vacancies: List<FavoriteVacancyDto>,
) {
    fun mapToDomain() =
        FavoriteResponse(
            vacancies = vacancies.map { it.mapToDomain() },
        )
}
