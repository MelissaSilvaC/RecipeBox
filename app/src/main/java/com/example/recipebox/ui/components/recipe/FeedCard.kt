package com.example.recipebox.ui.components.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.ui.theme.RecipeBoxTheme
import com.example.ui.theme.bodyFontFamily
import com.example.recipebox.R
import com.example.recipebox.ui.components.RecipeTag
import com.example.ui.theme.displayFontFamily
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun FeedCard(
    userName: String,
    recipeTitle: String,
    tags: List<Pair<String, Color>>,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            ) {
                // Header com o ícone e o nome do usuário
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Foto",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = userName,
                        fontFamily = bodyFontFamily, // Substitua por sua fonte Poppins
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Título da receita
                Text(
                    text = recipeTitle,
                    fontFamily = displayFontFamily, // Substitua por sua fonte AbeeZee
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal =  10.dp, vertical = 12.dp)
                )

                // Tags da receita
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tags) { (title, color) ->
                        RecipeTag(title = title, color = color)
                    }
                }

            }
        }
    }
}

@Preview
@Composable
private fun FeedCardPreview() {
    RecipeBoxTheme(dynamicColor = false) {
        FeedCard(
            userName = "Blau Blau",
            recipeTitle = "Macarrão a Bolonhesa com carne desfiada",
            tags = listOf(
                "Rápido" to Color(0xFFE91E63),
                "Sem lactose" to Color(0xFF00BCD4),
                "Ovos" to Color(0xFFF44336)
            ),
            onClick = { /*TODO*/ }
        )
    }
}