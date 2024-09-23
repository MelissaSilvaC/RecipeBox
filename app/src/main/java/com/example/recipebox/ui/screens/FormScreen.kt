package com.example.recipebox.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.data.Recipe
import com.example.recipebox.ui.theme.RecipeBoxTheme
import com.example.recipebox.viewModel.RecipeViewModel

@Composable
fun FormScreen(
    viewModel: RecipeViewModel,
    recipe: Recipe? = null,
    onNavigateToHome: () -> Unit
) {
    var title by remember { mutableStateOf(recipe?.title ?: "") }
    var ingredients by remember { mutableStateOf(recipe?.ingredients ?: "") }
    var preparing by remember { mutableStateOf(recipe?.preparing ?: "") }
    var preparingTime by remember { mutableStateOf(recipe?.preparingTime ?: "") }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(0.dp, 30.dp).verticalScroll(scrollState)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onNavigateToHome,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Arrow back")
            }
            Text(
                modifier = Modifier.padding(start = 5.dp).align(Alignment.CenterVertically),
                fontWeight = FontWeight.W500,
                fontSize = 25.sp,
                text = "Criar Receita"
            )
        }

        Column(
            modifier = Modifier.padding(20.dp, 30.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Titulo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(12.dp))

            TextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                maxLines = 10,
                label = { Text("Ingredientes") },
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )

            Spacer(modifier = Modifier.padding(12.dp))

            TextField(
                value = preparing,
                onValueChange = { preparing = it },
                maxLines = 10,
                label = { Text("Preparo") },
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )

            Spacer(modifier = Modifier.padding(12.dp))

            TextField(
                value = preparingTime,
                onValueChange = { preparingTime = it },
                label = { Text("Tempo de preparo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(14.dp))

            Button(modifier = Modifier.fillMaxWidth().padding(35.dp, 0.dp),
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    val newRecipe = recipe?.copy(
                        title = title,
                        ingredients = ingredients,
                        preparing = preparing,
                        preparingTime = preparingTime
                    ) ?: Recipe(
                        title = title,
                        ingredients = ingredients,
                        preparing = preparing,
                        preparingTime = preparingTime
                    )
                    viewModel.upsertRecipe(newRecipe)
                    onNavigateToHome()
                }
            ) {
                Text(fontSize = 18.sp, text = "Salvar")
            }
        }
    }
}

