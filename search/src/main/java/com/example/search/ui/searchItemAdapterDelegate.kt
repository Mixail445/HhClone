package com.example.search.ui

import com.example.common.common.BaseItem
import com.example.search.databinding.ItemSearchBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun searchItemAdapterDelegate(
    onItemClicked: (String) -> Unit,
    onFavoriteClicked: (String) -> Unit,
) = adapterDelegateViewBinding<VacanciesUi, BaseItem, ItemSearchBinding>(
    viewBinding = { layoutInflater, parent ->
        ItemSearchBinding.inflate(layoutInflater, parent, false)
    },
) {
    with(binding) {
        container.setOnClickListener {
            onItemClicked(item.itemId)
        }
        icFavorite.setOnClickListener {
            onFavoriteClicked(item.itemId)
        }
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
