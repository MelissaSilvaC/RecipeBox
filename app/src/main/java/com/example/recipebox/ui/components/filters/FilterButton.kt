package com.example.recipebox.ui.components.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.data.Filter

@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    filter: Filter,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        /*
        * modifier = Modifier
            .background(
                color = if (isSelected) Color(filter.color).copy(alpha = 0.8f) else Color(filter.color),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
        * */
    ) {
        Text(
            text = filter.name,
            color = if (isSelected) Color.Black else Color.White,
            fontSize = 16.sp
        )
    }
}