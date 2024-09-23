package com.example.recipebox.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recipebox.data.Recipe
import com.example.recipebox.navigation.AppDestination
import com.example.recipebox.ui.components.RecipeCard
import com.example.recipebox.ui.theme.RecipeBoxTheme
import com.example.recipebox.viewModel.RecipeViewModel

@Composable
fun DetailsScreen(
    viewModel: RecipeViewModel,
    recipe: Recipe,
    navController: NavController,
    onEditRecipe: (Recipe) -> Unit = { recipe ->
    navController.navigate("${AppDestination.Edit.route}/${recipe.id}")
    },
    NavigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(0.dp, 38.dp)
        .verticalScroll(scrollState),
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            IconButton(
                onClick = NavigateBack,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Arrow back")
            }

            Text(
                modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                text = "${recipe.title}"
            )
        }

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer, // Cor de fundo
            ),
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = "Ingredientes",
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Thin,
                text = "${recipe.ingredients}",
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer, // Cor de fundo
            ),
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = "Preparo",
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Thin,
                text = "${recipe.preparing}",
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer, // Cor de fundo
            ),
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = "Tempo de preparo",
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Thin,
                text = "${recipe.preparingTime}",
                color = MaterialTheme.colorScheme.secondary
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 14.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                onClick = {
                    viewModel.deleteRecipe(recipe)
                    NavigateBack()
                }
            ) {
                Row {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    Text(
                        modifier = Modifier.padding(15.dp, 0.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        text = "Excluir"
                    )
                }
            }

            Button(
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = { onEditRecipe(recipe) }
            ) {
                Row {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                    Text(
                        modifier = Modifier.padding(15.dp, 0.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        text = "Editar"
                    )
                }
            }

        }

    }
}
