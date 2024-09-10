package com.example.detaliSearch.ui

import android.graphics.drawable.Drawable
import com.example.navigation.Screens

class DetailSearchView {
    data class Model(
        val itemRc: List<ButtonUi> = emptyList(),
        val title: String = "",
        val salary: String = "",
        val experience: String = "",
        val appliedNumber: String = "",
        val lookingNumber: String = "",
        val address: String = "",
        val description: String = "",
        val responsibilities: String = "",
        val questions: List<String> = emptyList(),
        val schedules: String = "",
        val cardResponses: Boolean = true,
        val cardLoocking: Boolean = true,
        val company: String = "",
        val btOne: String = "",
        val btTwo: String = "",
        val btThree: String = "",
        val btFour: String = "",
        val iconFavorite: Drawable? = null,
    )

    sealed interface Event {
        data object OnClickResponse : Event

        data object OnClickFavorite : Event
    }

    sealed interface UiLabel {
        data class ShowModelScreen(
            val screens: Screens,
        ) : UiLabel
    }
}
