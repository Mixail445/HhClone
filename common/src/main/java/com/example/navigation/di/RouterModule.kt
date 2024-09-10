package com.example.navigation.di

import com.example.navigation.Router
import com.example.navigation.RouterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class RouterModule {
    @Provides
    @Named("Host")
    fun provideRouterHost(resourceProvider: ResourceProvider): Router =
        RouterImpl(resourceProvider.getFragmentContainerId(), resourceProvider.getButtonFragmentId())

//    @Provides
//    @Named("Child")
//    fun provideRouterChild(resourceProvider: ResourceProvider): Router = RouterImpl(resourceProvider.getHomeScreenContainerId())
}
