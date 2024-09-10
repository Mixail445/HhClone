package com.example.detaliSearch.ui

import android.graphics.drawable.Drawable
import com.example.common.common.BaseItem

data class DetailVacancyUi(
    val lookingNumber: String? = "",
    val salary: String = "",
    val questions: List<String> = emptyList(),
    val description: String? = "",
    val isFavorite: Drawable? = null,
    val appliedNumber: String? = "",
    val title: String = "",
    val responsibilities: String = "",
    val address: String = "",
    val company: String = "",
    val experience: String = "",
    val dataPublisher: String = "",
    val schedules: String = "",
    override val itemId: String = "",
) : BaseItem
