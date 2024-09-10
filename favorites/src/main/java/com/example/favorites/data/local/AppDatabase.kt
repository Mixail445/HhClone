package com.example.favorites.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteVacancyEntity::class], version = 1, exportSchema = false)
abstract class AppDatabaseFavorite : RoomDatabase() {
    abstract fun favoriteVacancyDao(): FavoriteDao
}
