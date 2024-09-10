package com.example.detaliSearch.di

import android.content.Context
import com.example.common.common.DispatchersProvider
import com.example.detaliSearch.data.DetailRepositoryImpl
import com.example.detaliSearch.data.remote.DetailApi
import com.example.detaliSearch.data.remote.DetailRemoteSourceImpl
import com.example.detaliSearch.domain.DetailRemoteSource
import com.example.detaliSearch.domain.DetailRepository
import com.example.detaliSearch.ui.DetailMapper
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class DetailModel {
    @Provides
    fun provideDetailApi(retrofit: Retrofit): DetailApi = retrofit.create(DetailApi::class.java)

    @Provides
    fun provideDetailApiSource(
        api: DetailApi,
        dispatchersProvider: DispatchersProvider,
    ): DetailRemoteSource = DetailRemoteSourceImpl(api, dispatchersProvider)

    @Provides
    fun provideDetailRepository(detailRepositoryImpl: DetailRepositoryImpl): DetailRepository = detailRepositoryImpl

    @Provides
    @Singleton
    fun provideDetailUiMapper(context: Context): DetailMapper = DetailMapper(context)
}
