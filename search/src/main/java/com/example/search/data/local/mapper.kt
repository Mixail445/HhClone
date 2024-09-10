package com.example.search.data.local

import com.example.data.FavoriteVacancyEntity
import com.example.search.domain.model.Vacancy

fun FavoriteVacancyEntity.mapToDomain() =
    Vacancy(
        id = id,
        title = title,
        address = address,
        publishedDate = publishedDate,
        experience = experience,
        company = company,
        isFavorite = true,
        lookingNumber = 1,
    )
