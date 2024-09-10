package com.example.detaliSearch.ui

import com.example.common.common.BaseItem
import com.example.common.utils.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class DetailButtonAdapter(
    val onItemClicked: (String) -> Unit,
) : AsyncListDifferDelegationAdapter<BaseItem>(diffUtils()) {
    init {
        delegatesManager
            .addDelegate(
                BUTTON_ITEM_VIEW_TYPE,
                detailItemAdapterDelegate(onItemClicked),
            )
    }

    companion object {
        const val BUTTON_ITEM_VIEW_TYPE = -1001
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
