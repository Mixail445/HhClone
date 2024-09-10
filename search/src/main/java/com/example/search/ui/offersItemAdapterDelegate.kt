package com.example.search.ui

import android.content.res.ColorStateList
import com.example.common.common.BaseItem
import com.example.search.databinding.ItemFastFilterBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun offersItemAdapterDelegate(onCardClick: (String) -> Unit) =
    adapterDelegateViewBinding<OffersUi, BaseItem, ItemFastFilterBinding>(
        viewBinding = { layoutInflater, parent ->
            ItemFastFilterBinding.inflate(layoutInflater, parent, false)
        },
    ) {
        with(binding) {
            container.setOnClickListener {
                onCardClick(item.url)
            }
        }
        bind {
            binding.run {
                with(item) {
                    binding.icon.setImageResource(item.icon)
                    binding.cardView.backgroundTintList = ColorStateList.valueOf(item.colorCard)
                    binding.title.text = title
                    binding.bottomText.text = bottomText
                }
            }
        }
    }
