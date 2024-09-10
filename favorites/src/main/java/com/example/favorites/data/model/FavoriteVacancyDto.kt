package com.example.favorites.data.model

import com.example.favorites.domain.model.FavoriteVacancy

data class FavoriteVacancyDto(
    val id: String,
    val lookingNumber: Long? = null,
    val title: String,
    val address: FavoriteAddressEntity,
    val company: String,
    val experience: FavoriteExperienceEntity,
    val publishedDate: String,
    val isFavorite: Boolean,
    val salary: FavoriteSalaryDto,
    val schedules: List<String>,
    val appliedNumber: Long? = null,
    val description: String? = null,
    val responsibilities: String,
    val questions: List<String>,
) {
    fun mapToDomain() =
        FavoriteVacancy(
            id = id,
            title = title,
            address = address.town,
            company = company,
            lookingNumber = lookingNumber?.toInt() ?: 0,
            experience = experience.previewText,
            publishedDate = publishedDate,
            isFavorite = isFavorite,
        )
}
