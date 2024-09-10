package com.example.favorites.ui

import com.example.common.common.BaseItem
import com.example.common.utils.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class FavoriteAdapter : AsyncListDifferDelegationAdapter<BaseItem>(diffUtils()) {
    init {
        delegatesManager
            .addDelegate(
                FAVORITE_ITEM_VIEW_TYPE,
                favoriteItemAdapterDelegate(),
            )
    }

    companion object {
        const val FAVORITE_ITEM_VIEW_TYPE = -1001
    }
}

private fun diffUtils() =
    itemCallback<BaseItem>(
        areItemsTheSame = { oldItem, newItem -> oldItem.itemId == newItem.itemId },
        areContentsTheSame = { oldItem, newItem -> oldItem == newItem },
        getChangePayload = { _, _ ->
            Any()
        },
    )
