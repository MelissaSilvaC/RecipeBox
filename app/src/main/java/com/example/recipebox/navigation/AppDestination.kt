package com.example.recipebox.navigation

import androidx.compose.material.icons.Icons

sealed class AppDestination(val route:String) {
    object Home : AppDestination("home")
    object Form : AppDestination("form")
    object Details : AppDestination("details")
}

