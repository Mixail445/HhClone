package com.example.detaliSearch.di

import com.example.common.di.NetworkModule
import com.example.detaliSearch.ui.DetailSearchFragment
import com.example.navigation.di.ResourceProvider
import com.example.navigation.di.RouterModule
import com.example.responsesScreen.ui.ModalScreenFragment
import com.example.search.di.SearchModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DetailModel::class, RouterModule::class, NetworkModule::class, SearchModel::class],
    dependencies = [ResourceProvider::class],
)
interface DetailComponent {
    @Component.Factory
    interface Factory {
        fun create(
            resourceProvider: ResourceProvider,
            searchModel: SearchModel,
        ): DetailComponent
    }

    fun inject(fragment: DetailSearchFragment)

    fun inject(fragment: ModalScreenFragment)
}
