package com.example.search.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteVacancyEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteVacancyDao(): SearchDao
}
