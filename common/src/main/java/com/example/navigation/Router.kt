package com.example.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationBarView

interface Router {
    fun init(
        fragment: Fragment?,
        fragmentManager: FragmentManager? = null,
        tabElementView: NavigationBarView? = null,
    )

    fun navigateTo(
        screen: Screens,
        bundle: Bundle? = null,
    )

    fun back()

    fun clear()

    fun setBadge(
        index: Int,
        count: Int,
        fragment: Fragment?,
    )
}
