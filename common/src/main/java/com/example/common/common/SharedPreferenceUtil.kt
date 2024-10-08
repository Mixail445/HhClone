package com.example.common.common

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Inject

class SharedPreferenceUtil<T : Any>
@Inject
constructor(
    context: Context,
    clazz: Class<T>,
) : SharedPreferencesOne<T> {
    companion object {
        private const val APP_PREFERENCES = "app_preferences"
    }

    private lateinit var preferences: SharedPreferences

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter = moshi.adapter(clazz)

    private fun init(context: Context) {
        preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    init {
        init(context)
    }

    override fun getData(key: String): T? =
        preferences
            .getString(
                key,
                "",
            ).let {
                if (it == "") {
                    null
                } else {
                    jsonAdapter.fromJson(it)
                }
            }

    override fun setData(
        key: String,
        data: T,
    ) {
        preferences.edit().putString(key, jsonAdapter.toJson(data)).apply()
    }

    override fun clearData() {
        preferences.all.clear()
    }
}