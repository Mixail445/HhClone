package com.example.detaliSearch.ui

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import javax.inject.Inject

class DetailMapper
    @Inject
    constructor(
        private val context: Context,
    ) {
        fun getIcon(isFavorite: Boolean): Drawable? {
            val drawableId =
                if (isFavorite) {
                    com.example.common.R.drawable.icon_heart_active
                } else {
                    com.example.common.R.drawable.icon_heart
                }
            return ResourcesCompat.getDrawable(context.resources, drawableId, null)
        }

        fun getResponsibilitiesForm(responsibilities: String): String = responsibilities.replace("\n", "<br>")

        fun getApplied(appliedNumber: Int?): String {
            if (appliedNumber == null || appliedNumber <= 0) {
                return ""
            }
            val personForm =
                when {
                    appliedNumber % 10 == 1 && appliedNumber % 100 != 11 -> "человек"
                    appliedNumber % 10 in 2..4 && (appliedNumber % 100 !in 12..14) -> "человека"
                    else -> "человеков"
                }
            return "$appliedNumber $personForm уже откликнулось"
        }

        fun getLookingNambFormat(lookingNumber: Int?): String? {
            if (lookingNumber == null || lookingNumber <= 0) {
                return null
            }
            val personForm =
                when {
                    lookingNumber % 10 == 1 && lookingNumber % 100 != 11 -> "человек"
                    lookingNumber % 10 in 2..4 && (lookingNumber % 100 !in 12..14) -> "человека"
                    else -> "человеков"
                }
            return "$lookingNumber $personForm сейчас смотрят"
        }

        fun getAddressFormat(address: List<String>): String {
            if (address.size < 3) {
                return "Некорректный адрес"
            }
            val city = address[0].takeIf { it.isNotBlank() } ?: "Неизвестный город"
            val street = address[1].takeIf { it.isNotBlank() } ?: "Неизвестная улица"
            val house = address[2].takeIf { it.isNotBlank() } ?: "Неизвестный дом"
            return "$city, $street, $house"
        }

        fun sheduleFormat(schedules: List<String>): String = schedules.joinToString(", ")
    }
