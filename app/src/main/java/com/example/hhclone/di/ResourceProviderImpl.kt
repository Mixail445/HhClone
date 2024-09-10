package com.example.hhclone.di

import com.example.hhclone.R
import com.example.navigation.di.ResourceProvider
import javax.inject.Inject

class ResourceProviderImpl
    @Inject
    constructor() : ResourceProvider {
        override fun getFragmentContainerId(): Int = R.id.fragmentContainer

        override fun getButtonFragmentId(): Int = R.id.navBar

        //   override fun getHomeScreenContainerId(): Int = R.id.homeScreenContainer
    }
