package com.example.login.inputTwo

import com.example.navigation.Screens

class InputTwoView {
        data class Model(
            val email:String,
            val bottomActive:Boolean = false
        )

        sealed interface Event {
            data object OnClickNext : Event
        }

        sealed interface UiLabel {
            data class ShowBottomFragment(val screens: Screens) : UiLabel
        }
}