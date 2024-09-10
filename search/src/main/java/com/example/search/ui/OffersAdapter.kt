package com.example.search.ui

import com.example.common.common.BaseItem
import com.example.common.utils.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class OffersAdapter(
    val onCardClick: (String) -> Unit,
) : AsyncListDifferDelegationAdapter<BaseItem>(diffUtils()) {
    init {
        delegatesManager
            .addDelegate(
                REVIEW_ITEM_VIEW_TYPE,
                offersItemAdapterDelegate(onCardClick),
            )
    }

    companion object {
        const val REVIEW_ITEM_VIEW_TYPE = -1001
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
