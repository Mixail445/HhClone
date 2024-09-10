package com.example.common.di

import com.example.common.common.DispatchersProvider

interface ResourceProvider {
    fun getDispatchersProvider(): DispatchersProvider
}
