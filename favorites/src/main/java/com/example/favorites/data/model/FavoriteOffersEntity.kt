package com.example.favorites.data.model

data class FavoriteOffersEntity(
    val id: String? = null,
    val title: String,
    val link: String,
    val button: FavoriteButtonEntity? = null,
)
