package com.example.search.data.model

import com.example.search.domain.model.Vacancy
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VacancyDto(
    val id: String,
    val lookingNumber: Long? = null,
    val title: String,
    val address: AddressDto,
    val company: String,
    val experience: ExperienceDto,
    val publishedDate: String,
    val isFavorite: Boolean,
    val salary: SalaryDto,
    val schedules: List<String>,
    val appliedNumber: Long? = null,
    val description: String? = null,
    val responsibilities: String,
    val questions: List<String>,
) {
    fun mapToDomain() =
        Vacancy(
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
