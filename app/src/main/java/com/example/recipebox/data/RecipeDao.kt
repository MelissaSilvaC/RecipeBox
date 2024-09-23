package com.example.recipebox.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Upsert
    suspend fun upsertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * from Recipe")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * from Recipe WHERE id = :id")
    fun getRecipeById(id: Int): Flow<Recipe>
}