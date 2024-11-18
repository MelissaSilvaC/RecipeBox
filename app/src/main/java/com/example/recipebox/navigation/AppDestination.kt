package com.example.recipebox.navigation

import com.example.recipebox.R
import com.example.recipebox.ui.components.BottomAppBarItem

sealed class AppDestination(val route:String) {
    //Rotas das telas de autenticação
    object Login : AppDestination("login")
    object Register : AppDestination("register")

    //Bottom Bar
    object Library : AppDestination("library")
    object Feed : AppDestination("feed")
    object Profile : AppDestination("profile")

    //Manipulando Receitas
    object Form : AppDestination("form")
    object Edit : AppDestination("edit")
    object Details : AppDestination("details")

    //Receitas de outros usuários
    object UserRecipeDetails : AppDestination("userRecipeDetails")
}

val bottomAppBarItems = listOf(
    BottomAppBarItem(
        label = "Biblioteca",
        iconResId = R.drawable.book,
        destination = AppDestination.Library
    ),
    BottomAppBarItem(
        label = "Feed",
        iconResId = R.drawable.library_books,
        destination = AppDestination.Feed
    ),
    BottomAppBarItem(
        label = "Perfil",
        iconResId = R.drawable.profile,
        destination = AppDestination.Profile
    )
)
