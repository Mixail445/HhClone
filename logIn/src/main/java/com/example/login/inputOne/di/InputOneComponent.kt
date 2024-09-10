package com.example.login.inputOne.di

import com.example.login.inputOne.ui.FragmentInputOne
import com.example.login.inputTwo.FragmentInputTwo
import com.example.navigation.di.ResourceProvider
import com.example.navigation.di.RouterModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [InputOneModule::class, RouterModule::class], dependencies = [ResourceProvider::class])
interface InputOneComponent {
    @Component.Factory
    interface Factory {
        fun create(resourceProvider: ResourceProvider): InputOneComponent
    }

    fun inject(fragment: FragmentInputOne)

    fun inject(fragment: FragmentInputTwo)
}
