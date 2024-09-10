package com.example.favorites.data.local

import com.example.data.FavoriteVacancyEntity
import com.example.favorites.domain.model.FavoriteVacancy

fun FavoriteVacancyEntity.mapToDomain() =
    FavoriteVacancy(
        id = id,
        title = title,
        address = address,
        publishedDate = publishedDate,
        experience = experience,
        company = company,
        isFavorite = true,
        lookingNumber = 1,
    )
