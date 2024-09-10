package com.example.favorites.di

import android.content.Context
import androidx.room.Room
import com.example.common.common.DispatchersProvider
import com.example.favorites.data.FavoriteRepositoryImpl
import com.example.favorites.data.local.AppDatabaseFavorite
import com.example.favorites.data.local.FavoriteDao
import com.example.favorites.data.local.FavoriteLocalSourceImpl
import com.example.favorites.domain.FavoriteLocalSource
import com.example.favorites.domain.FavoriteRepository
import com.example.favorites.ui.FavoriteMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FavoriteModule(
    private val context: Context,
) {
    @Provides
    fun provideFavoriteApiSource(
        dao: FavoriteDao,
        dispatchersProvider: DispatchersProvider,
    ): FavoriteLocalSource = FavoriteLocalSourceImpl(dao, dispatchersProvider)

    @Provides
    fun provideFavoriteRepository(repositoryImpl: FavoriteRepositoryImpl): FavoriteRepository = repositoryImpl

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideFavoriteUiMapper(context: Context): FavoriteMapper = FavoriteMapper(context)

    @Provides
    @Singleton
    fun provideFavoriteDatabase(context: Context): AppDatabaseFavorite =
        Room
            .databaseBuilder(
                context.applicationContext,
                AppDatabaseFavorite::class.java,
                "app_database",
            ).build()

    @Provides
    fun provideFavoriteDao(appDatabase: AppDatabaseFavorite): FavoriteDao = appDatabase.favoriteVacancyDao()
}
