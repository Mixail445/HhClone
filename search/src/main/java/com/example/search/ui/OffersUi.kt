package com.example.search.ui

import com.example.common.common.BaseItem

data class OffersUi(
    val url: String,
    val title: String,
    val bottomText: String,
    val colorCard: Int,
    val icon: Int,
    val id: String?,
    override val itemId: String,
) : BaseItem
