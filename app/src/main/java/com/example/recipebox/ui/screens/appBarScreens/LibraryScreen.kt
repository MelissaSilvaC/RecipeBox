package com.example.recipebox.ui.screens.appBarScreens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.recipebox.data.Recipe
import com.example.recipebox.data.Tag
import com.example.recipebox.data.filters
import com.example.recipebox.ui.components.recipe.RecipeCard
import com.example.recipebox.ui.components.SearchBarComposable
import com.example.recipebox.ui.components.filters.FiltersBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LibraryScreen(
    navigateToDetail: (Recipe) -> Unit,
    db: FirebaseFirestore
) {
    val searchText = remember { mutableStateOf("") }
    val recipes = remember { mutableStateListOf<Recipe>() }
    val auth = FirebaseAuth.getInstance()
    val currentUserEmail = auth.currentUser?.email

    val filteredRecipes = remember {
        derivedStateOf {
            recipes.filter { recipe ->
                recipe.title.contains(searchText.value, ignoreCase = true)
            }
        }
    }

    // Atualiza a lista de receitas ao sincronizar com o Firestore
    db.collection("Recipe")
        .addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w("LibraryScreen", "Listen failed.", e)
                return@addSnapshotListener
            }
            recipes.clear()

            snapshots?.forEach { document ->
                val userEmail = document.getString("userEmail") ?: ""
                if (userEmail == currentUserEmail) {
                    val recipe = Recipe(
                        id = document.id,
                        userEmail = userEmail,
                        userName = document.getString("userName") ?: "",
                        title = document.getString("title") ?: "",
                        ingredients = document.getString("ingredients") ?: "",
                        preparing = document.getString("preparing") ?: "",
                        preparingTime = document.getString("preparingTime") ?: "",
                        tags = document.get("tags")?.let { value ->
                            if (value is List<*>) {
                                value.mapNotNull { tagEntry ->
                                    (tagEntry as? String)?.let { tagName ->
                                        // Busca a cor associada à tag nos filtros
                                        val filter = filters.firstOrNull { filter ->
                                            filter.tagList.any { it.name == tagName }
                                        }
                                        val tagColor = filter?.tagList?.firstOrNull { it.name == tagName }?.color
                                        if (tagColor != null) Tag(name = tagName, color = tagColor) else null
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
        }

    // Layout da tela
    Column {
        // Barra de pesquisa
        SearchBarComposable(searchText)
        Row(modifier = Modifier.padding(vertical = 16.dp)){}

        // Lista de receitas
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredRecipes.value) { recipe ->
                recipe.tags?.let {
                    RecipeCard(
                        recipeName = recipe.title,
                        tags = recipe.tags,
                        onClick = { navigateToDetail(recipe) }
                    )
                }
            }
        }
    }
}

