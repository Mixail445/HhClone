package com.example.favorites.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.favorites.domain.model.FavoriteVacancy

@Entity(tableName = "favorite_vacancies")
data class FavoriteVacancyEntity(
    @PrimaryKey val id: String,
    val title: String,
    val company: String,
    val publishedDate: String,
    val address: String,
    val experience: String,
) {
    fun mapToDomain() =
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
}
