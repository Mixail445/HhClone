package com.example.search.domain.model

import com.example.data.FavoriteVacancyEntity
import com.example.search.ui.SearchUiMapper
import com.example.search.ui.VacanciesUi

data class Vacancy(
    val id: String,
    val title: String,
    val address: String,
    val company: String,
    val lookingNumber: Int,
    val experience: String,
    val publishedDate: String,
    val isFavorite: Boolean,
) {
    fun mapToUi(uiMapper: SearchUiMapper) =
        VacanciesUi(
            lookingNumber = uiMapper.getLookingNumber(lookingNumber),
            title = title,
            address = address,
            company = company,
            experience = experience,
            dataPublisher = uiMapper.getFormatedDate(publishedDate),
            itemId = id,
            favorite = uiMapper.getDrawable(isFavorite),
        )

    fun mapToEmpty(): FavoriteVacancyEntity =
        FavoriteVacancyEntity(
            id = id,
            title = title,
            address = address,
            company = company,
            experience = experience,
            publishedDate = publishedDate,
        )
}
