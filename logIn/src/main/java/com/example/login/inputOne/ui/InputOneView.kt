package com.example.login.inputOne.ui

import android.os.Bundle
import com.example.navigation.Screens

class InputOneView {
    data class Model(
        val textEdit: String,
        val isBottomActive: Boolean = false,
        val showIconClearText: Boolean = false,
        val wrongEmail: Boolean,
        val showIconStart: Boolean = true,
        val error: Boolean = false,
    )

    sealed interface Event {
        data class OnTextChanged(
            val text: String,
        ) : Event

        data class OnClickNext(
            val email: Bundle,
        ) : Event

        data object OnClickClearText : Event
    }

    sealed interface UiLabel {
        data class ShowInputTwoScreen(
            val screens: Screens,
        ) : UiLabel
    }
}
