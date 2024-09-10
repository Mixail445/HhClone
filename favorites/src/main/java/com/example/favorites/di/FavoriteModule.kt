package com.example.favorites.di

import android.content.Context
import com.example.common.common.DispatchersProvider
import com.example.data.SearchDao
import com.example.favorites.data.FavoriteRepositoryImpl
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
        dao: SearchDao,
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
}
