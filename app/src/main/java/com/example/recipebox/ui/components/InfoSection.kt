package com.example.recipebox.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoSection(
    title: String,
    info: String
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            text = title,
            color = if(isSystemInDarkTheme()){
                MaterialTheme.colorScheme.primary
            }else{
                MaterialTheme.colorScheme.secondary
            }
        )

        Text(
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Thin,
            text = info,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}