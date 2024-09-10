package com.example.favorites.ui

class FavoriteView {
    data class Model(
        val itemRc: List<FavoriteUi> = emptyList(),
        val text: String,
    )

    sealed interface Event

    sealed interface UiLabel
}
