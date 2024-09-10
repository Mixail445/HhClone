package com.example.search.di

import com.example.common.di.NetworkModule
import com.example.navigation.di.ResourceProvider
import com.example.navigation.di.RouterModule
import com.example.search.ui.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [SearchModel::class, RouterModule::class, NetworkModule::class],
    dependencies = [ResourceProvider::class],
)
interface SearchComponent {
    @Component.Factory
    interface Factory {
        fun create(
            resourceProvider: ResourceProvider,
            searchModel: SearchModel,
        ): SearchComponent
    }

    fun inject(fragment: SearchFragment)
}
