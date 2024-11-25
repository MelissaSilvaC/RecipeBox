package com.example.recipebox.ui.screens.loginScreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.ui.components.ButtonAndLink
import com.example.recipebox.ui.components.LogoAuth
import com.example.recipebox.ui.components.PasswordTextField
import com.example.ui.theme.displayFontFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.userProfileChangeRequest

@Composable
fun RegisterScreen(
    navigateToLogin: () -> Unit,
    navigateToFeed: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 18.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        val auth = FirebaseAuth.getInstance()

        fun handleLogin() {
            email = email.trim()
            name = name.trim()
            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                errorMessage = "Preencha todos os campos."
                return
            }

            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { result ->
                val user = result.user
                val profileUpdates = userProfileChangeRequest {
                    displayName = name
                }
                user?.updateProfile(profileUpdates)?.addOnSuccessListener {
                        navigateToLogin()
                }?.addOnFailureListener {
                    errorMessage = "Falha ao atualizar perfil."
                }
                navigateToLogin()
            }.addOnFailureListener { error ->
                if (error is FirebaseAuthException) {
                    errorMessage = when (error.errorCode) {
                        "ERROR_INVALID_EMAIL" -> "E-mail inválido."
                        "ERROR_WEAK_PASSWORD" -> "Senha muito pequena, o mínimo é 6 digitos."
                        "ERROR_EMAIL_ALREADY_IN_USE" -> "E-mail já cadastrado."
                        else -> "Erro desconhecido: ${error.message}"
                    }
                }
            }
        }

        Text(
            fontSize = 26.sp,
            fontFamily = displayFontFamily,
            color = if(isSystemInDarkTheme()){
                MaterialTheme.colorScheme.onSecondaryContainer
            }else{
                MaterialTheme.colorScheme.primary
            },
            text = "Tenha as suas receitas na palma da sua mão!",
            modifier = Modifier.padding(bottom = 34.dp, start = 8.dp, end = 8.dp)
        )

        LogoAuth()

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Nome") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

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

        PasswordTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it},
            label = { Text("Confirme sua senha") }
        )

        ButtonAndLink(
            buttonClick = {
                if (password == confirmPassword) {
                    handleLogin()
                } else {
                    errorMessage = "As senhas não coincidem."
                }},
            linkClick = { navigateToLogin() },
            errorMessage = errorMessage,
            buttonText = "Cadastrar-se"
        )

    }
}
