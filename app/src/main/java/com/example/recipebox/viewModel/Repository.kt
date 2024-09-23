package com.example.recipebox.viewModel

import com.example.recipebox.data.Recipe
import com.example.recipebox.data.RecipeDatabase

class Repository(private val db : RecipeDatabase) {
    suspend fun upsertRecipe(recipe : Recipe){
        db.recipeDao().upsertRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe : Recipe) {
        db.recipeDao().deleteRecipe(recipe)
    }

    fun getAllRecipes() = db.recipeDao().getAllRecipes()

    fun getRecipeById(id: Int) = db.recipeDao().getRecipeById(id)
}