package com.example.hhclone.di

import com.example.navigation.di.ResourceProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ResourceModule {
    @Binds
    abstract fun bindResourceProvider(impl: ResourceProviderImpl): ResourceProvider
}
