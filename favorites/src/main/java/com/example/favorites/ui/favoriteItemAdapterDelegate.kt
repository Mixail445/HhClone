package com.example.favorites.ui

import com.example.common.common.BaseItem
import com.example.favorites.databinding.ItemFavoriteBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun favoriteItemAdapterDelegate() =
    adapterDelegateViewBinding<FavoriteUi, BaseItem, ItemFavoriteBinding>(
        viewBinding = { layoutInflater, parent ->
            ItemFavoriteBinding.inflate(layoutInflater, parent, false)
        },
    ) {
        with(binding) {
        }
        bind {
            binding.run {
                with(item) {
                    binding.tvLookingNumber.text = lookingNumber
                    binding.tvTitle.text = title
                    binding.tvCompany.text = company
                    binding.tvAddress.text = address
                    binding.tvData.text = dataPublisher
                    binding.tvExperience.text = experience
                    binding.icFavorite.setImageDrawable(favorite)
                }
            }
        }
    }
