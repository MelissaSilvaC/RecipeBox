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
import com.example.recipebox.data.Tag
import com.example.recipebox.data.filters
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

    // Atualizando a listagem de forma síncrona com snapshots
    db.collection("Recipe")
        .addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.e("FeedScreen", "Erro ao buscar receitas: ${e.message}")
                return@addSnapshotListener
            }

            recipes.clear()

            snapshots?.forEach { document ->
                val userEmail = document.getString("userEmail") ?: return@forEach

                // Criando o objeto Recipe a partir do documento Firestore
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
                            value.mapNotNull { tagName ->
                                (tagName as? String)?.let { name ->
                                    // Busca a cor associada à tag nos filtros
                                    val filter = filters.firstOrNull { filter ->
                                        filter.tagList.any { it.name == name }
                                    }
                                    val tagColor = filter?.tagList?.firstOrNull { it.name == name }?.color
                                    if (tagColor != null) Tag(name = name, color = tagColor) else null
                                }
                            }
                        } else {
                            emptyList()
                        }
                    } ?: emptyList()
                )

                recipes.add(recipe) // Adiciona a receita à lista
            }
        }

    Column {
        // Barra de pesquisa
        SearchBarComposable(searchText)
        Row(modifier = Modifier.padding(vertical = 16.dp)) {}

        // Listagem de receitas
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

