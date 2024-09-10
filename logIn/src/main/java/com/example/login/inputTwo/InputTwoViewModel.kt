package com.example.login.inputTwo

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

private const val STATE_KEY_INPUT_TWO_SCREEN = "fragment_input_two"

class InputTwoViewModel
    @AssistedInject
    constructor(
        @Assisted private val state: SavedStateHandle,
        @Assisted private val email: String,
    ) : ViewModel() {
        private val _uiState =
            MutableStateFlow(
                state.get<InputTwoView.Model>(STATE_KEY_INPUT_TWO_SCREEN) ?: InputTwoView.Model(email),
            )
        val uiState: StateFlow<InputTwoView.Model> = _uiState.asStateFlow()

        private val _uiLabels = SingleLiveData<InputTwoView.UiLabel>()
        val uiLabels: LiveData<InputTwoView.UiLabel> get() = _uiLabels

        private val otpDigits = MutableStateFlow(arrayOf("", "", "", ""))

        fun onOtpDigitChanged(
            index: Int,
            value: String,
        ) {
            val updatedOtp = otpDigits.value.copyOf()
            updatedOtp[index] = value
            otpDigits.value = updatedOtp

            _uiState.value =
                _uiState.value.copy(
                    bottomActive = updatedOtp.all { it.length == 1 },
                )
        }

        fun onEvent(event: InputTwoView.Event): Unit =
            when (event) {
                InputTwoView.Event.OnClickNext -> showBottomFragment()
            }

        private fun showBottomFragment() {
            _uiLabels.value = InputTwoView.UiLabel.ShowBottomFragment(Screens.BottomScreen)
        }

        @AssistedFactory
        interface Factory {
            fun build(
                @Assisted state: SavedStateHandle,
                @Assisted email: String,
            ): InputTwoViewModel
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
