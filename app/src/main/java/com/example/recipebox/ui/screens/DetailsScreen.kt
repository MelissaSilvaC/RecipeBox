package com.example.recipebox.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.R
import com.example.recipebox.data.Recipe
import com.example.recipebox.ui.components.InfoSection
import com.example.recipebox.ui.components.RecipeTag
import com.example.ui.theme.bodyFontFamily
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun DetailsScreen(
    recipe: Recipe,
    navigateToEdit: (Recipe) -> Unit,
    NavigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    fun deleteRecipe(recipeId: String, db: FirebaseFirestore, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("Recipe").document(recipeId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 38.dp)
        .verticalScroll(scrollState),
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            IconButton(
                onClick = { NavigateBack() },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Navigate Back",
                    modifier = Modifier.size(30.dp)
                )
            }

            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if(isSystemInDarkTheme()){
                    Color.White
                }else{
                    MaterialTheme.colorScheme.onTertiaryContainer
                },
                text = recipe.title
            )
        }

        //Lista de Tags
        LazyRow(
                modifier = Modifier.padding(bottom = 8.dp, top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
            items(recipe.tags) { (tagName, color) ->
                RecipeTag(title = tagName, color = color)
            }
        }

        InfoSection(
            title = "Ingredientes",
            info = recipe.ingredients
        )

        InfoSection(
            title = "Preparo",
            info = recipe.preparing
        )

        InfoSection(
            title = "Tempo de preparo",
            info = recipe.preparingTime
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                onClick = {
                    deleteRecipe(
                        recipeId = recipe.id,
                        db = FirebaseFirestore.getInstance(),
                        onSuccess = {
                            NavigateBack()
                        },
                        onFailure = { exception ->
                            Log.e("DetailsScreen", "Erro ao excluir receita: ${exception.message}")
                        }
                    )
                }
            ) {
                Row {
                    Icon(
                        contentDescription = "Deletar",
                        painter = painterResource(id = R.drawable.delete),
                    )
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
                    containerColor = if(isSystemInDarkTheme()){
                        MaterialTheme.colorScheme.primary
                    }else{
                        MaterialTheme.colorScheme.primaryContainer
                    },
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    navigateToEdit(recipe)
                }
            ) {
                Row {
                    Icon(
                        contentDescription = "Editar",
                        painter = painterResource(id = R.drawable.edit),
                    )
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
