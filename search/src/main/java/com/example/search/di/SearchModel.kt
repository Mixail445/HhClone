package com.example.search.di

import android.content.Context
import androidx.room.Room
import com.example.common.common.DispatchersProvider
import com.example.common.utils.ResultWrapper
import com.example.search.data.SearchRepositoryImpl
import com.example.search.data.local.AppDatabase
import com.example.search.data.local.SearchDao
import com.example.search.data.local.SearchLocalSourceImpl
import com.example.search.data.remote.SearchApi
import com.example.search.data.remote.SearchRemoteSourceImpl
import com.example.search.domain.SearchLocalSource
import com.example.search.domain.SearchRemoteSource
import com.example.search.domain.VacancyRepository
import com.example.search.ui.SearchUiMapper
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class SearchModel(
    private val context: Context,
) {
    @Provides
    fun provideSearchApi(retrofit: Retrofit): SearchApi = retrofit.create(SearchApi::class.java)

    @Provides
    fun provideSearchApiSource(
        api: SearchApi,
        dispatchersProvider: DispatchersProvider,
    ): SearchRemoteSource = SearchRemoteSourceImpl(api, dispatchersProvider)

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideSearchUiMapper(context: Context): SearchUiMapper = SearchUiMapper(context)

    // Provide Database
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase =
        Room
            .databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database",
            ).build()

    @Provides
    fun provideSearchDao(appDatabase: AppDatabase): SearchDao = appDatabase.favoriteVacancyDao()

    @Provides
    fun provideVacancyRepository(
        remoteSource: SearchRemoteSource,
        localSource: SearchLocalSource,
        wrapper: ResultWrapper,
    ): VacancyRepository = SearchRepositoryImpl(remoteSource, localSource, wrapper)

    @Provides
    fun provideSearchLocalSource(
        dao: SearchDao,
        dispatchersProvider: DispatchersProvider,
    ): SearchLocalSource = SearchLocalSourceImpl(dao, dispatchersProvider)
}
