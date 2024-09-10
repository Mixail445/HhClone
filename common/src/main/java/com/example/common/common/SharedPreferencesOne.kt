package com.example.common.common

interface SharedPreferencesOne<T> {
    fun getData(key: String): T?

    fun clearData()

    fun setData(
        key: String,
        data: T,
    )
}