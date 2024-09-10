package com.example.search.ui

import android.os.Bundle
import com.example.navigation.Screens

class SearchView {
    data class Model(
        val itemMainRc: List<VacanciesUi> = emptyList(),
        val itemChildRc: List<OffersUi> = emptyList(),
        val modeAllRcItem: Boolean = false,
        val countVacancy: String = "",
        val stateIcon: Int? = null,
        val countFavorite: Int = 0,
    )

    sealed interface Event {
        data class OnClickMainRc(
            val bundle: Bundle,
        ) : Event

        data class OnClickChildRc(
            val url: String,
        ) : Event

        data class OnClickFavoriteRc(
            val id: String,
        ) : Event

        data object OnClickAllRcItem : Event

        data object OnClickIcTextEdit : Event
    }

    sealed interface UiLabel {
        data class ShowBrowse(
            val url: String,
        ) : UiLabel

        data class ShowDetailScreen(
            val screens: Screens,
            val id: Bundle,
        ) : UiLabel
    }
}
