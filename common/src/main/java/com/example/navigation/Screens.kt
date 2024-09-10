package com.example.navigation

import android.os.Bundle

sealed class Screens {
    data object InputOneScreen : Screens()

    data class InputTwoScreen(
        val email: Bundle,
    ) : Screens()

    data object BottomScreen : Screens()

    data class DetailScreen(
        val id: Bundle,
    ) : Screens()

    data object ModalScreenOne : Screens()

    data object ModalScreenTwo : Screens()
}
