package com.example.detaliSearch.data.model

import com.example.detaliSearch.domain.model.DetailVacancy
import com.example.search.data.model.AddressDto
import com.example.search.data.model.ExperienceDto
import com.example.search.data.model.SalaryDto

data class DetailVacancyDto(
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
        DetailVacancy(
            salary = salary.full,
            isFavorite = isFavorite,
            id = id,
            questions = questions,
            responsibilities = responsibilities,
            schedules = schedules,
            description = description,
            title = title,
            address = listOf(address.town, address.street, address.house),
            appliedNumber = appliedNumber?.toInt(),
            company = company,
            lookingNumber = lookingNumber?.toInt() ?: 0,
            experience = experience.text,
            publishedDate = publishedDate,
        )
}
