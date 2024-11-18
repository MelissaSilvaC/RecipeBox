package com.example.recipebox.ui.screens.appBarScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.R
import com.example.ui.theme.displayFontFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val auth = FirebaseAuth.getInstance()
    val userInfo = auth.currentUser // Obtemos o usuário autenticado

    // Nome e e-mail do usuário
    val displayName = userInfo?.displayName ?: "Usuário Desconhecido"
    val email = userInfo?.email ?: "E-mail não disponível"

    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(vertical = 18.dp, horizontal = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Foto",
                tint = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier.size(50.dp)
            )
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(
                    text = displayName,
                    fontFamily = displayFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    fontSize = 22.sp
                )
                Text(
                    text = email,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    fontSize = 16.sp
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                auth.signOut()
                navigateToLogin()
            }
        ) {
            Text(
                text = "Sair",
                fontFamily = displayFontFamily,
                fontSize = 16.sp
            )
        }

    }
}