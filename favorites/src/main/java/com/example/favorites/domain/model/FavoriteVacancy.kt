package com.example.favorites.domain.model

import com.example.favorites.ui.FavoriteMapper
import com.example.favorites.ui.FavoriteUi

data class FavoriteVacancy(
    val id: String,
    val title: String,
    val address: String,
    val company: String,
    val lookingNumber: Int,
    val experience: String,
    val publishedDate: String,
    val isFavorite: Boolean,
) {
    fun mapToUi(mapper: FavoriteMapper) =
        FavoriteUi(
            lookingNumber = mapper.getLookingNumber(lookingNumber),
            title = title,
            address = address,
            company = company,
            experience = experience,
            dataPublisher = publishedDate,
            itemId = id,
            favorite = mapper.getDrawable(isFavorite),
        )
}
