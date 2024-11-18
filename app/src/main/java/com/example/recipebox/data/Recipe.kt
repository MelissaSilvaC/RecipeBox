package com.example.recipebox.data

import androidx.compose.ui.graphics.Color
import java.io.Serializable

data class Recipe(
    val id: String,
    val userEmail: String,
    val userName: String,
    val title: String,
    val ingredients: String,
    val preparing: String,
    val preparingTime: String,
    val tags: List<Pair<String, Color>>
) : Serializable