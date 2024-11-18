package com.example.recipebox.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.bodyFontFamily

@Composable
fun RecipeTag(
    title: String,
    color: Color, modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .background(color, shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = title,
            fontFamily = bodyFontFamily,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}