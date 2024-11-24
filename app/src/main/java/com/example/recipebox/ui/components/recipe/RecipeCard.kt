package com.example.recipebox.ui.components.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.data.Tag
import com.example.recipebox.ui.components.RecipeTag
import com.example.recipebox.ui.theme.RecipeBoxTheme
import com.example.ui.theme.displayFontFamily


@Composable
fun RecipeCard(
    recipeName: String,
    onClick: () -> Unit,
    tags: List<Tag>,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp, 0.dp, 16.dp, 0.dp),
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, top = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
    ) {
        Row(modifier = Modifier.padding(vertical = 4.dp)) {}

        Text(
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp,
            fontFamily = displayFontFamily,
            text = recipeName,
            color = MaterialTheme.colorScheme.secondary
        )

        // Tags da receita
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tags) { (title, color) ->
                RecipeTag(title = title, color = color)
            }
        }

        Row(modifier = Modifier.padding(vertical = 10.dp)) {}
    }
}