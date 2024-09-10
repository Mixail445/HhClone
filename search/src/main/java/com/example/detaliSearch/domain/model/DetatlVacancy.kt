package com.example.detaliSearch.domain.model

import com.example.detaliSearch.ui.ButtonUi
import com.example.detaliSearch.ui.DetailMapper
import com.example.detaliSearch.ui.DetailVacancyUi

data class DetailVacancy(
    val id: String,
    val salary: String,
    val schedules: List<String>,
    val title: String,
    val address: List<String>,
    val questions: List<String>,
    val description: String?,
    val company: String,
    val isFavorite: Boolean,
    val appliedNumber: Int?,
    val lookingNumber: Int?,
    val responsibilities: String,
    val experience: String,
    val publishedDate: String,
) {
    fun mapToUi(mapper: DetailMapper) =
        DetailVacancyUi(
            salary = salary,
            isFavorite = mapper.getIcon(isFavorite),
            description = mapper.getResponsibilitiesForm(description ?: ""),
            responsibilities = mapper.getResponsibilitiesForm(responsibilities),
            appliedNumber = mapper.getApplied(appliedNumber),
            questions = questions,
            lookingNumber = mapper.getLookingNambFormat(lookingNumber),
            schedules = mapper.sheduleFormat(schedules),
            title = title,
            address = mapper.getAddressFormat(address),
            company = company,
            experience = experience,
            dataPublisher = publishedDate,
            itemId = id,
        )

    fun mapToButton() =
        ButtonUi(
            itemId = id,
            text = questions.first(),
        )
}
