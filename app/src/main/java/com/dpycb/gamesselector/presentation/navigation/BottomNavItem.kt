package com.dpycb.gamesselector.presentation.navigation

import com.dpycb.gamesselector.R

sealed class BottomNavItem(val iconRed: Int, val title: String, val screenRoute: String) {
    object MainScreenPage : BottomNavItem(R.drawable.ic_baseline_home_24, "Главная", "mainScreen")
    object Settings : BottomNavItem(R.drawable.ic_baseline_settings_24, "Настройки", "settings")
}
