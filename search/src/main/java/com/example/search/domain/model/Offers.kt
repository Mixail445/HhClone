package com.example.search.domain.model

import com.example.search.ui.OffersUi
import com.example.search.ui.SearchUiMapper

data class Offers(
    val id: String?,
    val title: String,
    val button: String?,
    val url: String,
) {
    fun mapToUi(mapper: SearchUiMapper) =
        OffersUi(
            title = title,
            bottomText = button ?: "",
            itemId = id ?: "",
            icon = mapper.getIcon(id ?: "").first ?: 1,
            url = url,
            colorCard = mapper.getIcon(id ?: "").second,
            id = id,
        )
}
