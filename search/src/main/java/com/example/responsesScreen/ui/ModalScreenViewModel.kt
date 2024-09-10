package com.example.responsesScreen.ui

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ModalScreenViewModel
    @AssistedInject
    constructor(
        @Assisted private val state: SavedStateHandle,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun build(
                @Assisted state: SavedStateHandle,
            ): ModalScreenViewModel
        }

        class LambdaFactory<T : ViewModel>(
            savedStateRegistryOwner: SavedStateRegistryOwner,
            private val create: (handle: SavedStateHandle) -> T,
        ) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle,
            ): T = create(handle) as T
        }
    }
