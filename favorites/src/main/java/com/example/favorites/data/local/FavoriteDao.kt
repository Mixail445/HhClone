package com.example.favorites.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_vacancies")
    suspend fun getAllFavorites(): List<FavoriteVacancyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(vacancy: FavoriteVacancyEntity)

    @Delete
    suspend fun removeFavorite(vacancy: FavoriteVacancyEntity)

    @Query("SELECT * FROM favorite_vacancies WHERE id = :vacancyId")
    suspend fun getFavoriteById(vacancyId: String): FavoriteVacancyEntity?
}
