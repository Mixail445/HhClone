package com.example.login.inputOne.ui

import android.os.Bundle
import android.util.Patterns
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.common.common.SingleLiveData
import com.example.navigation.Screens
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val STATE_KEY_INPUT_SCREEN = "fragment_input"

class InputOneViewModel
    @AssistedInject
    constructor(
        @Assisted private val state: SavedStateHandle,
    ) : ViewModel() {
        private val _uiState =
            MutableStateFlow(
                state.get<InputOneView.Model>(STATE_KEY_INPUT_SCREEN) ?: InputOneView.Model("", wrongEmail = false),
            )
        val uiState: StateFlow<InputOneView.Model> = _uiState.asStateFlow()

        private val _uiLabels = SingleLiveData<InputOneView.UiLabel>()
        val uiLabels: LiveData<InputOneView.UiLabel> get() = _uiLabels

        fun onEvent(event: InputOneView.Event) {
            when (event) {
                is InputOneView.Event.OnClickNext -> handleClickNext(event.email)
                is InputOneView.Event.OnTextChanged -> handleTextChanged(event.text)
                InputOneView.Event.OnClickClearText -> clearText()
            }
        }

        private fun handleClickNext(email: Bundle) {
            val isValid = _uiState.value.textEdit.isValidEmail()
            if (isValid) {
                _uiState.update {
                    it.copy(
                        wrongEmail = false,
                    )
                }
                _uiLabels.value = InputOneView.UiLabel.ShowInputTwoScreen(Screens.InputTwoScreen(email))
            } else {
                _uiState.update {
                    it.copy(wrongEmail = true)
                }
            }
        }

        private fun handleTextChanged(text: String) {
            _uiState.update {
                if (text.isValidEmail() || text.isEmpty()) {
                    it.copy(
                        textEdit = text,
                        isBottomActive = text.isNotEmpty(),
                        showIconStart = false,
                        error = false,
                        wrongEmail = false,
                    )
                } else {
                    it.copy(
                        textEdit = text,
                        isBottomActive = text.isNotEmpty(),
                        showIconStart = false,
                        error = false,
                        wrongEmail = false,
                    )
                }
            }
        }

        private fun clearText() {
            _uiState.update {
                it.copy(
                    textEdit = "",
                    isBottomActive = false,
                    showIconStart = true,
                    wrongEmail = false,
                )
            }
        }

        private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

        @AssistedFactory
        interface Factory {
            fun build(
                @Assisted state: SavedStateHandle,
            ): InputOneViewModel
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
