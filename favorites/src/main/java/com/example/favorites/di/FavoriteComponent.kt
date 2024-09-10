package com.example.favorites.di

import com.example.common.di.NetworkModule
import com.example.favorites.ui.FavoriteFragment
import com.example.navigation.di.ResourceProvider
import com.example.navigation.di.RouterModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [FavoriteModule::class, RouterModule::class, NetworkModule::class],
    dependencies = [ResourceProvider::class],
)
interface FavoriteComponent {
    @Component.Factory
    interface Factory {
        fun create(
            resourceProvider: ResourceProvider,
            favoriteModule: FavoriteModule,
        ): FavoriteComponent
    }

    fun inject(fragment: FavoriteFragment)
}
