package com.example.recipebox.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipebox.data.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel (private val repository: Repository) : ViewModel() {
    fun getRecipe() = repository.getAllRecipes().asLiveData(viewModelScope.coroutineContext)

    fun getRecipeById(id: Int): LiveData<Recipe> {
        return repository.getRecipeById(id).asLiveData(viewModelScope.coroutineContext)
    }

    fun upsertRecipe(recipe: Recipe){
        viewModelScope.launch {
            repository.upsertRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe : Recipe) {
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
        }
    }

}