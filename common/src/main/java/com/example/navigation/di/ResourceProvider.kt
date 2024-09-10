package com.example.navigation.di

interface ResourceProvider {
    fun getFragmentContainerId(): Int

    fun getButtonFragmentId(): Int
    // fun getHomeScreenContainerId(): Int
}
