package com.example.recipebox.ui.screens.appBarScreens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.recipebox.data.Recipe
import com.example.recipebox.ui.components.recipe.FeedCard
import com.example.recipebox.ui.components.SearchBarComposable
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun FeedScreen(
    navigateToUserRecipeDetail: (Recipe) -> Unit,
    db: FirebaseFirestore,
) {
    val searchText = remember { mutableStateOf("") }
    val recipes = remember { mutableStateListOf<Recipe>() }

    val filteredRecipes = recipes.filter { recipe ->
        recipe.title.contains(searchText.value, ignoreCase = true)
    }

    //Atualizando a listagem de forma síncrona com snapshots
    db.collection("Recipe")
        .addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w("LibraryScreen", "Listen failed.", e)
                return@addSnapshotListener
            }
            // Limpando a lista antes de adicionar novos dados
            recipes.clear()
            // Percorre os documentos e adiciona na lista
            snapshots?.forEach { document ->
                val recipe = Recipe(
                    id = document.getString("id") ?: "",
                    userEmail = document.getString("userEmail") ?: "",
                    userName = document.getString("userName") ?: "",
                    title = document.getString("title") ?: "",
                    ingredients = document.getString("ingredients") ?: "",
                    preparing = document.getString("preparing") ?: "",
                    preparingTime = document.getString("preparingTime") ?: "",
                    tags = document.get("tags")?.let { value ->
                        if (value is List<*>) {
                            value.mapNotNull { tagEntry ->
                                val parts = (tagEntry as? String)?.split(",")?.map { it.trim() }
                                if (parts != null && parts.size == 2) {
                                    val title = parts[0]
                                    val colorString = parts[1]
                                    try {
                                        val truncatedColorString = colorString.take(8)
                                        val color = Color(android.graphics.Color.parseColor("#$truncatedColorString"))
                                        title to color
                                    } catch (e: IllegalArgumentException) {
                                        null
                                    }
                                } else {
                                    null
                                }
                            }
                        } else {
                            emptyList()
                        }
                    } ?: emptyList()
                )
                recipes.add(recipe)
            }
        }

    Column {
        SearchBarComposable(searchText)
        Row(modifier = Modifier.padding(vertical = 16.dp)){}

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredRecipes) { recipe ->
                FeedCard(
                    userName = recipe.userName,
                    recipeTitle = recipe.title,
                    tags = recipe.tags,
                    onClick = { navigateToUserRecipeDetail(recipe) }
                )
            }
        }

    }
}
