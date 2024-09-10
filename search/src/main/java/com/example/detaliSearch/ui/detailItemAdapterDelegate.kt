package com.example.detaliSearch.ui

import com.example.common.common.BaseItem
import com.example.search.databinding.ItemButtonBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun detailItemAdapterDelegate(onItemClicked: (String) -> Unit) =
    adapterDelegateViewBinding<ButtonUi, BaseItem, ItemButtonBinding>(
        viewBinding = { layoutInflater, parent ->
            ItemButtonBinding.inflate(layoutInflater, parent, false)
        },
    ) {
        with(binding) {
            bvItemRc.setOnClickListener {
                onItemClicked(item.text)
            }
        }
        bind {
            binding.run {
                with(item) {
                    bvItemRc.text = item.text
                }
            }
        }
    }
