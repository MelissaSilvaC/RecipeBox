package com.example.recipebox.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val ingredients: String,
    val preparing: String,
    val preparingTime: String
)