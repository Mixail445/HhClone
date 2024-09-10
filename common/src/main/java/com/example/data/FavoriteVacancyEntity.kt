package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies")
data class FavoriteVacancyEntity(
    @PrimaryKey val id: String,
    val title: String,
    val company: String,
    val publishedDate: String,
    val address: String,
    val experience: String,
)
