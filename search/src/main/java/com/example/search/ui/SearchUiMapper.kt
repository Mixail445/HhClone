package com.example.search.ui

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.search.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SearchUiMapper
    @Inject
    constructor(
        private val context: Context,
    ) {
        private val drawableFavorite by lazy {
            ContextCompat.getDrawable(context, com.example.common.R.drawable.icon_heart_active)
        }
        private val drawableNotFavorite by lazy {
            ContextCompat.getDrawable(context, com.example.common.R.drawable.icon_heart)
        }

        fun getIcon(id: String): Pair<Int?, Int> {
            when (id) {
                "near_vacancies" -> {
                    return Pair(
                        com.example.common.R.drawable.icon_heart,
                        context.resources.getColor(com.example.common.R.color.dark_blue, null),
                    )
                }
                "level_up_resume" -> {
                    return Pair(
                        com.example.common.R.drawable.icon_star,
                        context.resources.getColor(com.example.common.R.color.dark_green, null),
                    )
                }
                "temporary_job" -> {
                    return Pair(
                        com.example.common.R.drawable.icon_vacancy_list,
                        context.resources.getColor(com.example.common.R.color.dark_green, null),
                    )
                }
                else -> {
                    return Pair(
                        com.example.common.R.drawable.icon_search,
                        context.resources.getColor(com.example.common.R.color.dark_green, null),
                    )
                }
            }
        }

        fun getLookingNumber(people: Int): String {
            val lookingNumber = people.toString()
            val word =
                when {
                    people % 10 == 1 && people % 100 != 11 -> "человек"
                    people % 10 in 2..4 && (people % 100 < 10 || people % 100 > 20) -> "человека"
                    else -> "человеков"
                }
            return "Сейчас просматривает $lookingNumber $word"
        }

        fun getFavoriteIcon(isFavorite: Boolean): Drawable? {
            val drawableId =
                if (isFavorite) {
                    com.example.common.R.drawable.icon_heart_active
                } else {
                    com.example.common.R.drawable.icon_heart
                }
            return ResourcesCompat.getDrawable(context.resources, drawableId, null)
        }

        fun getFormatedDate(date: String): String {
            val localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
            val day = localDate.dayOfMonth
            val month = localDate.monthValue
            val monthName = getMonthName(month, day)
            return "Опубликовано $day $monthName"
        }

        fun getMonthName(
            month: Int,
            day: Int,
        ): String =
            when (month) {
                1 -> "января"
                2 -> "февраля"
                3 -> "марта"
                4 -> "апреля"
                5 -> "мая"
                6 -> "июня"
                7 -> "июля"
                8 -> "августа"
                9 -> "сентября"
                10 -> "октября"
                11 -> "ноября"
                12 -> "декабря"
                else -> ""
            }

        fun getTextVacancyCount(count: Int): String {
            val vacancyWord =
                when {
                    count % 10 == 1 && count % 100 != 11 -> "вакансия"
                    count % 10 in 2..4 && (count % 100 < 10 || count % 100 > 20) -> "вакансии"
                    else -> "вакансий"
                }
            return "$count $vacancyWord"
        }

        fun getDrawable(isFavorite: Boolean): Drawable? =
            if (isFavorite) {
                drawableFavorite
            } else {
                drawableNotFavorite
            }
    }
