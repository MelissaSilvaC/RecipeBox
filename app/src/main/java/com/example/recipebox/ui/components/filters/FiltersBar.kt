package com.example.recipebox.ui.components.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.recipebox.data.Filter
import com.example.recipebox.data.Recipe
import com.example.recipebox.data.Tag
import com.example.recipebox.data.filters

@Composable
fun FiltersBar(
    recipes: List<Recipe>,
    onFilteredRecipesChange: (List<Recipe>) -> Unit,
    onSelectedTagsChange: (List<Pair<String, Color>>) -> Unit
) {
    val selectedTags = remember { mutableStateListOf<Pair<String, Color>>() }
    val selectedFilter = remember { mutableStateOf<Filter?>(null) }

    Column(modifier = Modifier.padding(top = 16.dp, bottom = 35.dp)) {
        // Barra de filtros
        LazyRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filters) { filter ->
                FilterButton(
                    filter = filter,
                    isSelected = selectedFilter.value == filter,
                    onClick = {
                        selectedFilter.value = if (selectedFilter.value == filter) null else filter
                    }
                )
            }
        }

        // Exibe as tags do filtro selecionado
        selectedFilter.value?.let { filter ->
            LazyRow(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filter.tagList) { tag ->
                    val tagPair = tag.name to tag.color
                    TagButton(
                        tag = tag,
                        isSelected = selectedTags.contains(tagPair),
                        onClick = {
                            if (selectedTags.contains(tagPair)) {
                                selectedTags.remove(tagPair)
                            } else {
                                selectedTags.add(tagPair)
                            }

                            // Atualiza receitas filtradas
                            val filtered = recipes.filter { recipe ->
                                selectedTags.all { selectedTag ->
                                    recipe.tags?.any { it.first == selectedTag.first } == true
                                }
                            }

                            // Passa as atualizações para a tela
                            onFilteredRecipesChange(filtered)
                            onSelectedTagsChange(selectedTags)
                        }
                    )
                }
            }
        }
    }
}

