package com.example.favorites.ui

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.example.common.common.SingleLiveData
import com.example.favorites.domain.FavoriteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val STATE_KEY_FAVORITE = "fragment_favorite"

class FavoriteViewModel
    @AssistedInject
    constructor(
        @Assisted private val state: SavedStateHandle,
        private val repository: FavoriteRepository,
        private val mapper: FavoriteMapper,
    ) : ViewModel() {
        private val _uiState =
            MutableStateFlow(
                state.get<FavoriteView.Model>(STATE_KEY_FAVORITE) ?: FavoriteView.Model(text = ""),
            )
        val uiState: StateFlow<FavoriteView.Model> = _uiState.asStateFlow()

        private val _uiLabels = SingleLiveData<FavoriteView.UiLabel>()
        val uiLabels: LiveData<FavoriteView.UiLabel> get() = _uiLabels

        private var _favorite: List<FavoriteUi> = emptyList()
        private var _textCount: Int = 0

        init {
            viewModelScope.launch { initData() }
        }

        private suspend fun initData() = fetchReviews()

        private suspend fun fetchReviews() {
            val favorites = repository.getFavorites().map { it.mapToUi(mapper) }
            val textCount = repository.getFavoriteVacanciesCount()

            _favorite = favorites
            _textCount = textCount

            _uiState.update {
                it.copy(
                    itemRc = _favorite,
                    text = mapper.getTextVacancyCount(_textCount),
                )
            }
        }

        @AssistedFactory
        interface Factory {
            fun build(
                @Assisted state: SavedStateHandle,
            ): FavoriteViewModel
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
