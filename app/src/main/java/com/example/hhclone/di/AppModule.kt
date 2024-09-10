package com.example.hhclone.di

import android.app.Application
import android.content.Context
import com.example.hhclone.R
import com.example.navigation.di.ResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }


}