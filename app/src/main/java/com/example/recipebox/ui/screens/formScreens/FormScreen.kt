package com.example.recipebox.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.R
import com.example.recipebox.data.filters
import com.example.ui.theme.displayFontFamily
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun FormScreen(
    NavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var preparing by remember { mutableStateOf("") }
    var preparingTime by remember { mutableStateOf("") }
    val selectedTags = remember { mutableStateListOf<String>() }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(0.dp, 30.dp).verticalScroll(scrollState)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = NavigateBack,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Voltar",
                    tint = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.size(30.dp)
                )
            }
            Text(
                modifier = Modifier.padding(start = 5.dp).align(Alignment.CenterVertically),
                fontWeight = FontWeight.W500,
                fontFamily = displayFontFamily,
                fontSize = 25.sp,
                text = "Criar Receita"
            )
        }

        Column(
            modifier = Modifier.padding(20.dp, 22.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Titulo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(12.dp))

            TextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                maxLines = 10,
                label = { Text("Ingredientes") },
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )

            Spacer(modifier = Modifier.padding(12.dp))

            TextField(
                value = preparing,
                onValueChange = { preparing = it },
                maxLines = 10,
                label = { Text("Preparo") },
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )

            Spacer(modifier = Modifier.padding(12.dp))

            TextField(
                value = preparingTime,
                onValueChange = { preparingTime = it },
                label = { Text("Tempo de preparo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(14.dp))

            // Área de tags
            Text(
                text = "Escolha Tags para sua receita",
                fontWeight = FontWeight.Bold,
                fontFamily = displayFontFamily,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            filters.forEach { filter ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = filter.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        items(filter.tagList) { tag ->
                            TagChip(
                                tagName = tag.name,
                                color = tag.color,
                                isSelected = selectedTags.contains(tag.name),
                                onSelect = {
                                    if (selectedTags.contains(tag.name)) {
                                        selectedTags.remove(tag.name)
                                    } else {
                                        selectedTags.add(tag.name)
                                    }
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(18.dp))

            Button(modifier = Modifier.fillMaxWidth().padding(35.dp, 0.dp),
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    val auth = FirebaseAuth.getInstance()
                    val currentUser = auth.currentUser

                    if (currentUser != null) {
                        val userEmail = currentUser.email ?: "Email desconhecido"
                        val userName = currentUser.displayName ?: "Usuário desconhecido"

                        // Cria o objeto de receita
                        val newRecipe = hashMapOf(
                            "userEmail" to userEmail,
                            "userName" to userName,
                            "title" to title,
                            "ingredients" to ingredients,
                            "preparing" to preparing,
                            "preparingTime" to preparingTime,
                            "tags" to selectedTags
                        )

                        FirebaseFirestore.getInstance().collection("Recipe")
                            .add(newRecipe)
                            .addOnSuccessListener {
                                Log.d("FormScreen", "Receita cadastrada com sucesso!")
                                NavigateBack()
                            }
                            .addOnFailureListener { e ->
                                Log.e("FormScreen", "Erro ao cadastrar receita: ${e.message}")
                            }
                    } else {
                        Log.e("FormScreen", "Usuário não autenticado")
                    }
                }
            ) { Text(fontSize = 18.sp, text = "Salvar") }
        }
    }
}

@Composable
fun TagChip(
    tagName: String,
    color: Color,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Text(
        text = tagName,
        color = if (isSelected) Color.White else Color.Black,
        modifier = Modifier
            .background(
                color = if (isSelected) color else color.copy(alpha = 0.4f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onSelect() },
        fontSize = 12.sp
    )
}