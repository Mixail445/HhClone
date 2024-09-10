package com.example.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.common.R
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import javax.inject.Inject

class RouterImpl
    @Inject
    constructor(
        private val controller: Int,
        private val navigationView: Int,
    ) : Router {
        private var navController: NavController? = null

        override fun init(
            fragment: Fragment?,
            fragmentManager: FragmentManager?,
            tabElementView: NavigationBarView?,
        ) {
            val navHostFragment = fragmentManager?.findFragmentById(controller) as NavHostFragment?
            navController = navHostFragment?.navController ?: fragment?.findNavController()
            navController?.let {
                if (tabElementView != null) {
                    NavigationUI.setupWithNavController(tabElementView, it)
                }
            }
        }

        override fun navigateTo(
            screen: Screens,
            bundle: Bundle?,
        ) {
            when (screen) {
                Screens.InputOneScreen -> showOneScreen()
                is Screens.InputTwoScreen -> showTwoScreen(screen.email)
                Screens.ModalScreenOne -> showDialogOne()
                Screens.ModalScreenTwo -> TODO()
                Screens.BottomScreen -> showBottomScreen()
                is Screens.DetailScreen -> showDetailScreen(screen.id)
            }
        }

        private fun showDialogOne() {
            navController?.navigate(R.id.action_detailFragment_to_dialogFragment)
        }

        private fun showDetailScreen(bundle: Bundle?) {
            navController?.navigate(R.id.action_searchFragment_to_detailFragment, bundle)
        }

        private fun showBottomScreen() {
            navController?.navigate(R.id.log_nav_To_bottom_nav)
        }

        private fun showTwoScreen(email: Bundle) {
            navController?.navigate(R.id.action_fragmentInputOne_to_fragmentInputTwo, email)
        }

        private fun showOneScreen() {
            navController?.navigate(R.id.fragmentInputOne)
        }

        override fun back() {
            navController?.popBackStack()
        }

        override fun clear() {
            navController = null
        }

        @SuppressLint("RestrictedApi")
        override fun setBadge(
            index: Int,
            count: Int,
            fragment: Fragment?,
        ) {
            val navigationView =
                fragment?.requireActivity()?.findViewById<BottomNavigationView>(navigationView)
            val menuView = navigationView?.getChildAt(0) as BottomNavigationMenuView
            val itemView = menuView.getChildAt(index) as BottomNavigationItemView

            val existingBadgeView = itemView.findViewById<View>(R.id.tv_badge)

            if (count > 0) {
                if (existingBadgeView != null) {
                    val countView = existingBadgeView.findViewById<TextView>(R.id.tv_badge)
                    countView.text = count.toString()
                    countView.visibility = View.VISIBLE
                } else {
                    val badgeView =
                        LayoutInflater
                            .from(fragment.requireActivity())
                            .inflate(R.layout.layout_badge_view, menuView, false)
                    itemView.addView(badgeView)

                    val countView = badgeView.findViewById<TextView>(R.id.tv_badge)
                    countView.text = count.toString()
                    countView.visibility = View.VISIBLE
                }
            } else {
                existingBadgeView?.let {
                    it.visibility = View.GONE
                }
            }
        }
    }
