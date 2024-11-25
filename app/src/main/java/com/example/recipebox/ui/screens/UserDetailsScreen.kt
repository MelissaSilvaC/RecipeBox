package com.example.recipebox.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.R
import com.example.recipebox.data.Recipe
import com.example.recipebox.ui.components.InfoSection
import com.example.recipebox.ui.components.RecipeTag
import com.example.recipebox.ui.theme.RecipeBoxTheme
import com.example.ui.theme.displayFontFamily
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserDetailsScreen(
    recipe: Recipe,
    NavigateBack: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 38.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column(modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { NavigateBack() },
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Navigate Back",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(
                        fontSize = 20.sp,
                        color = if(isSystemInDarkTheme()){
                            Color.White
                        }else{
                            MaterialTheme.colorScheme.onTertiaryContainer
                        },
                        text = recipe.userName
                    )
                }

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = displayFontFamily,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    text = recipe.title
                )
            }
        }

        LazyRow(
            modifier = Modifier.padding(bottom = 8.dp, top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recipe.tags) { (tagName, color) ->
                RecipeTag(title = tagName, color = color)
            }
        }

        // Informações da receita
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
    }
}
