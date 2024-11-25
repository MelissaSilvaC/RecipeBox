package com.example.recipebox.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.R
import com.example.ui.theme.displayFontFamily

@Composable
fun LogoAuth(modifier: Modifier = Modifier) {
    Image(painter = painterResource(
        id = R.drawable.box_fill),
        contentDescription = "RecipeBox",
        colorFilter = if(isSystemInDarkTheme()){
            ColorFilter.tint(MaterialTheme.colorScheme.primary)
        }else{
            ColorFilter.tint(MaterialTheme.colorScheme.secondaryContainer)
        },
        modifier = Modifier.size(50.dp)
    )

    Text(
        fontSize = 24.sp,
        fontFamily = displayFontFamily,
        color = if(isSystemInDarkTheme()){
            MaterialTheme.colorScheme.primary
        }else{
            MaterialTheme.colorScheme.secondaryContainer
        },
        text = "Entre no RecipeBox",
        modifier = Modifier.padding(bottom = 30.dp)
    )
}