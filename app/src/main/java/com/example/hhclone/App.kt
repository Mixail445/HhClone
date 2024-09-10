package com.example.hhclone

import android.app.Application
import com.example.favorites.di.FavoriteModule
import com.example.hhclone.di.AppComponent
import com.example.hhclone.di.DaggerAppComponent
import com.example.navigation.di.ResourceProvider
import com.example.navigation.di.ResourceProviderProvider
import com.example.search.di.SearchModel

class App :
    Application(),
    ResourceProviderProvider {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerAppComponent.factory().create(
                this,
                SearchModel(this.applicationContext),
                FavoriteModule(this.applicationContext),
            )
    }

    override fun getResourceProvider(): ResourceProvider = appComponent.resourceProvider()
}
