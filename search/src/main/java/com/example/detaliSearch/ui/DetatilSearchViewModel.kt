package com.example.detaliSearch.ui

import android.util.Log
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.example.common.common.SingleLiveData
import com.example.common.utils.onError
import com.example.common.utils.onSuccess
import com.example.detaliSearch.domain.DetailRepository
import com.example.navigation.Screens
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val STATE_KEY_SEARCH_DETAILS_SCREEN = "fragment_search_details"

class DetailSearchViewModel
    @AssistedInject
    constructor(
        private val mapper: DetailMapper,
        @Assisted private val id: String,
        @Assisted private val state: SavedStateHandle,
        private val repository: DetailRepository,
    ) : ViewModel() {
        private val _uiState =
            MutableStateFlow(
                state.get<DetailSearchView.Model>(STATE_KEY_SEARCH_DETAILS_SCREEN) ?: DetailSearchView.Model(),
            )
        val uiState: StateFlow<DetailSearchView.Model> = _uiState.asStateFlow()

        private val _uiLabels = SingleLiveData<DetailSearchView.UiLabel>()
        val uiLabels: LiveData<DetailSearchView.UiLabel> get() = _uiLabels

        fun onEvent(event: DetailSearchView.Event) {
            when (event) {
                DetailSearchView.Event.OnClickFavorite -> TODO()
                is DetailSearchView.Event.OnClickResponse -> showModalScreen()
            }
        }

        private var _vacancy: DetailVacancyUi =
            DetailVacancyUi()

        private fun showModalScreen() {
            _uiLabels.value = DetailSearchView.UiLabel.ShowModelScreen(Screens.ModalScreenOne)
        }

        init {
            viewModelScope.launch {
                initData(id)
            }
        }

        private suspend fun initData(id: String) {
            requestReviews(id)
        }

        private suspend fun requestReviews(id: String) {
            repository
                .getVacancy(id)
                .onSuccess { responses ->
                    _vacancy = responses?.mapToUi(mapper) ?: _vacancy
                    if (responses != null) {
                        _uiState.update {
                            it.copy(
                                itemRc = listOf(responses.mapToButton()),
                                title = _vacancy.title,
                                salary = _vacancy.salary,
                                experience = _vacancy.experience,
                                appliedNumber = _vacancy.appliedNumber ?: "",
                                lookingNumber = _vacancy.lookingNumber ?: "",
                                address = _vacancy.address,
                                description = _vacancy.description ?: "",
                                responsibilities = _vacancy.responsibilities,
                                questions = _vacancy.questions,
                                schedules = _vacancy.schedules,
                                cardResponses = _vacancy.appliedNumber != null,
                                cardLoocking = _vacancy.lookingNumber != null,
                                company = _vacancy.company,
                                iconFavorite = _vacancy.isFavorite,
                            )
                        }
                    }
                }.onError { Log.d("errppr", it.message.toString()) }
        }

        @AssistedFactory
        interface Factory {
            fun build(
                @Assisted id: String,
                @Assisted state: SavedStateHandle,
            ): DetailSearchViewModel
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
