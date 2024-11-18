package com.example.recipebox.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipebox.navigation.AppDestination
import com.example.recipebox.navigation.bottomAppBarItems
import com.example.recipebox.ui.theme.RecipeBoxTheme

class BottomAppBarItem(
    val label: String,
    val iconResId: Int,
    val destination: AppDestination
)

@Composable
fun RecipeBoxAppBar(
    item: BottomAppBarItem,
    items: List<BottomAppBarItem> = emptyList(),
    onItemChange: (BottomAppBarItem) -> Unit = {},
    modifier: Modifier = Modifier.background(MaterialTheme.colorScheme.onSecondaryContainer)
) {
    NavigationBar(modifier) {
        items.forEach {
            val label = it.label
            val icon = painterResource(id = it.iconResId)

            val iconColor = if (item.label == label) {
                MaterialTheme.colorScheme.inverseOnSurface // Cor quando selecionado
            } else {
                MaterialTheme.colorScheme.primary // Cor padrão quando não selecionado
            }

            NavigationBarItem(
                icon = {
                    Image(
                        painter = icon,
                        contentDescription = label,
                        colorFilter = ColorFilter.tint(iconColor)
                    )
                },
                label = { Text(label) },
                selected = item.label == label,
                onClick = { onItemChange(it) }
            )
        }
    }
}

@Preview
@Composable
private fun RecipeBoxAppBarPreview() {
    RecipeBoxTheme(dynamicColor = false) {
        RecipeBoxAppBar(
            item = bottomAppBarItems.first(),
            items = bottomAppBarItems
        )
    }
}