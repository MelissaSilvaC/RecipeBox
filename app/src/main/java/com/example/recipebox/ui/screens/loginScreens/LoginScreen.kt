package com.example.recipebox.ui.screens.loginScreens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.ui.components.ButtonAndLink
import com.example.recipebox.ui.components.LogoAuth
import com.example.recipebox.ui.components.PasswordTextField
import com.example.ui.theme.displayFontFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToFeed: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 18.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        val auth = FirebaseAuth.getInstance()

        fun handleLogin() {
            email = email.trim()
            if (email.isEmpty() || password.isEmpty()) {
                errorMessage = "Preencha todos os campos."
                return
            }
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                navigateToFeed()
            }.addOnFailureListener { error ->
                if (error is FirebaseAuthException) {
                    errorMessage = when (error.errorCode) {
                        "ERROR_USER_NOT_FOUND" -> "Usuário não encontrado. Dados incorretos."
                        else -> "Erro ao realizar o login: ${error.message}"
                    }
                } else {
                    errorMessage = "Erro desconhecido: ${error.message}"
                }
            }
        }

        Text(
            fontSize = 30.sp,
            fontFamily = displayFontFamily,
            color = if(isSystemInDarkTheme()){
                MaterialTheme.colorScheme.onSecondaryContainer
            }else{
                MaterialTheme.colorScheme.primary
            },
            text = "Bem vindo de volta!",
            modifier = Modifier.padding(bottom = 34.dp)
        )

        LogoAuth()

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        PasswordTextField(
            value = password,
            onValueChange = { password = it},
            label = { Text("Senha") }
        )

        ButtonAndLink(
            buttonClick = { handleLogin() },
            linkClick = { navigateToRegister() },
            errorMessage = errorMessage,
            buttonText = "Entrar"
        )


    }
}