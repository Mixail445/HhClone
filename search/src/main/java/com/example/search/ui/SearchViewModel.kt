package com.example.search.ui

import android.os.Bundle
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
import com.example.navigation.Screens
import com.example.search.domain.VacancyRepository
import com.example.search.domain.model.Vacancy
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val STATE_KEY_SEARCH_SCREEN = "fragment_search"

class SearchViewModel
    @AssistedInject
    constructor(
        @Assisted private val state: SavedStateHandle,
        private val mapper: SearchUiMapper,
        private val repository: VacancyRepository,
    ) : ViewModel() {
        private val _uiState =
            MutableStateFlow(
                state.get<SearchView.Model>(STATE_KEY_SEARCH_SCREEN) ?: SearchView.Model(),
            )
        val uiState: StateFlow<SearchView.Model> = _uiState.asStateFlow()

        private val _uiLabels = SingleLiveData<SearchView.UiLabel>()
        val uiLabels: LiveData<SearchView.UiLabel> get() = _uiLabels

        private var _vacancy: List<VacanciesUi> = emptyList()
        private var _favorites: MutableSet<String> = mutableSetOf()
        private var _offers: List<OffersUi> = emptyList()

        init {
            viewModelScope.launch {
                initData()
            }
        }

        fun onEvent(event: SearchView.Event) {
            when (event) {
                is SearchView.Event.OnClickChildRc -> showBrowser(event.url)
                is SearchView.Event.OnClickMainRc -> showDetailScreen(event.bundle)
                is SearchView.Event.OnClickFavoriteRc -> toggleFavorite(event.id)
                SearchView.Event.OnClickAllRcItem -> showAllItem()
                SearchView.Event.OnClickIcTextEdit -> showBackState()
            }
        }

        private fun showBackState() {
            _uiState.update {
                it.copy(
                    modeAllRcItem = false,
                    itemMainRc = _vacancy.take(3),
                    itemChildRc = _offers,
                )
            }
        }

        private suspend fun initData() {
            val favoriteVacanciesCount = repository.getFavoriteVacanciesCount()
            _favorites = repository.getFavorites().map { it.id }.toMutableSet()
            requestVacancies(favoriteVacanciesCount)
        }

        private suspend fun requestVacancies(favoriteCount: Int) {
            repository
                .getVacancy()
                .onSuccess { response ->
                    _vacancy =
                        response.vacancies.map { vacancy ->
                            vacancy.mapToUi(mapper).copy(favorite = mapper.getDrawable(_favorites.contains(vacancy.id)))
                        }
                    _offers =
                        response.offers.map {
                            it.mapToUi(mapper)
                        }
                    updateVacancyUI(favoriteCount)
                }.onError { error: Throwable ->
                    Log.d("ERROR", error.message.toString())
                }
        }

        private fun updateVacancyUI(favoriteCount: Int) {
            val vacanciesToShow =
                if (_uiState.value.modeAllRcItem) {
                    _vacancy
                } else {
                    _vacancy.take(3)
                }

            _uiState.update {
                it.copy(
                    itemMainRc = vacanciesToShow,
                    countVacancy = mapper.getTextVacancyCount(_vacancy.size),
                    countFavorite = favoriteCount,
                    itemChildRc = _offers,
                )
            }
        }

        fun toggleFavorite(id: String) {
            viewModelScope.launch {
                if (_favorites.contains(id)) {
                    _favorites.remove(id)
                    repository.removeVacancyFromFavorites(id)
                } else {
                    _favorites.add(id)
                    val vacancy = _vacancy.find { it.itemId == id }
                    vacancy?.let {
                        val favoriteVacancy =
                            Vacancy(
                                id = it.itemId,
                                title = it.title,
                                company = it.company,
                                publishedDate = it.dataPublisher,
                                address = it.address,
                                experience = it.experience,
                                isFavorite = true,
                                lookingNumber = 1,
                            )
                        repository.addVacancyToFavorites(favoriteVacancy)
                    }
                }

                _vacancy =
                    _vacancy.map { vacancy ->
                        vacancy.copy(favorite = mapper.getDrawable(_favorites.contains(vacancy.itemId)))
                    }

                val favoriteVacanciesCount = _favorites.size
                updateVacancyUI(favoriteVacanciesCount)
            }
        }

        private fun showAllItem() {
            _uiState.update {
                it.copy(
                    itemMainRc = _vacancy,
                    modeAllRcItem = true,
                )
            }
        }

        private fun showDetailScreen(bundle: Bundle) {
            _uiLabels.value = SearchView.UiLabel.ShowDetailScreen(Screens.DetailScreen(bundle), bundle)
        }

        private fun showBrowser(url: String) {
            _uiLabels.value = SearchView.UiLabel.ShowBrowse(url)
        }

        @AssistedFactory
        interface Factory {
            fun build(
                @Assisted state: SavedStateHandle,
            ): SearchViewModel
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
