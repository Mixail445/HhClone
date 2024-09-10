package com.example.hhclone.di

import android.app.Application
import com.example.common.di.NetworkModule
import com.example.favorites.di.FavoriteModule
import com.example.hhclone.presentation.MainActivity
import com.example.navigation.di.ResourceProvider
import com.example.navigation.di.RouterModule
import com.example.search.di.SearchModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AppModule::class, ResourceModule::class, RouterModule::class, SearchModel::class, FavoriteModule::class, NetworkModule::class],
)
@Singleton
interface AppComponent {
    fun inject(application: Application)

    fun resourceProvider(): ResourceProvider

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            searchModel: SearchModel,
            favoriteModule: FavoriteModule,
        ): AppComponent
    }
}
