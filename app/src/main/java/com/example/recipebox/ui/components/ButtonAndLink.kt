package com.example.recipebox.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonAndLink(
    modifier: Modifier = Modifier,
    buttonClick: () -> Unit,
    buttonText: String,
    errorMessage: String?,
    linkClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth().height(40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
        onClick = buttonClick
    ) {
        Text(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            text = buttonText,
            color = if(isSystemInDarkTheme()){
                MaterialTheme.colorScheme.onTertiaryContainer
            }else{
                Color.White
            }
        )
    }

    AnimatedVisibility(visible = errorMessage != null) {
        errorMessage?.let {
            Text(
                text = it,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Medium
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    TextButton( onClick = linkClick  ) {
        Text(
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline,
            color = if(isSystemInDarkTheme()){
                MaterialTheme.colorScheme.onSecondaryContainer
            }else{
                MaterialTheme.colorScheme.primary
            },
            text ="Não tem uma conta? Cadastre-se aqui!"
        )
    }
}