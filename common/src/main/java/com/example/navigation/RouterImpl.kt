package com.example.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.common.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import javax.inject.Inject

class RouterImpl
    @Inject
    constructor(
        @IdRes private val controller: Int,
        @IdRes private val navigationViewId: Int,
    ) : Router {
        private var navController: NavController? = null

        override fun init(
            fragment: Fragment?,
            fragmentManager: FragmentManager?,
            tabElementView: NavigationBarView?,
        ) {
            val navHostFragment = fragmentManager?.findFragmentById(controller) as? NavHostFragment
            navController = navHostFragment?.navController ?: fragment?.findNavController()

            navController?.let { controller ->
                tabElementView?.let { NavigationUI.setupWithNavController(it, controller) }
            }
        }

        override fun navigateTo(
            screen: Screens,
            bundle: Bundle?,
        ) {
            when (screen) {
                Screens.InputOneScreen -> navigate(R.id.fragmentInputOne)
                is Screens.InputTwoScreen -> navigate(R.id.action_fragmentInputOne_to_fragmentInputTwo, screen.email)
                Screens.ModalScreenOne -> navigate(R.id.action_detailFragment_to_dialogFragment)
                Screens.ModalScreenTwo -> TODO()
                Screens.BottomScreen -> navigate(R.id.log_nav_To_bottom_nav)
                is Screens.DetailScreen -> navigate(R.id.action_searchFragment_to_detailFragment, bundle)
            }
        }

        private fun navigate(
            @IdRes actionId: Int,
            bundle: Bundle? = null,
        ) {
            navController?.navigate(actionId, bundle)
        }

        override fun back() {
            navController?.popBackStack()
        }

        override fun clear() {
            navController = null
        }

        override fun setBadge(
            index: Int,
            count: Int,
            fragment: Fragment?,
        ) {
            val navigationView = fragment?.requireActivity()?.findViewById<BottomNavigationView>(navigationViewId)

            navigationView?.let {
                val menuItem = it.menu.getItem(index)
                val badge = it.getOrCreateBadge(menuItem.itemId)

                if (count > 0) {
                    badge.isVisible = true
                    badge.number = count
                } else {
                    badge.isVisible = false
                }
            }
        }
    }
