package com.example.common.utils

import android.content.Context
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorHandel
@Inject
constructor(
    private val context: Context,
) {
    fun handleError(e: Throwable): String {
        when (e) {
            is UnknownHostException -> context.getString(androidx.appcompat.R.string.search_menu_title)
            else -> context.getString(androidx.appcompat.R.string.search_menu_title)
        }
        return e.message.toString()
    }
}