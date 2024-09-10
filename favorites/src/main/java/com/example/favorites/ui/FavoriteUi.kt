package com.example.favorites.ui

import android.graphics.drawable.Drawable
import com.example.common.common.BaseItem
import kotlinx.parcelize.RawValue

data class FavoriteUi(
    val lookingNumber: String,
    val favorite: @RawValue Drawable?,
    val title: String,
    val address: String,
    val company: String,
    val experience: String,
    val dataPublisher: String,
    override val itemId: String,
) : BaseItem
