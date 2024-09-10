package com.example.search.ui

import android.graphics.drawable.Drawable
import android.os.Parcelable
import com.example.common.common.BaseItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class VacanciesUi(
    val lookingNumber: String,
    val favorite: @RawValue Drawable?,
    val title: String,
    val address: String,
    val company: String,
    val experience: String,
    val dataPublisher: String,
    override val itemId: String,
) : BaseItem,
    Parcelable
