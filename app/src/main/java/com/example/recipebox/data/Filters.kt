package com.example.recipebox.data

import androidx.compose.ui.graphics.Color

data class Filter(
    val name: String,
    val tagList: List<Tag>
)

data class Tag(
    val name: String,
    val color: Color
)

val filters = listOf(
    Filter("Sem Carne", listOf(
        Tag(name = "Vegano", color = Color(0xFF597D64)),
        Tag(name = "Vegetariano", color = Color(0xFF597D64)),
    )  ),

    Filter("Intolerantes", listOf(
        Tag(name = "Sem Frutose", color = Color(0xFF00BCD4)),
        Tag(name = "Sem Glúten", color = Color(0xFF00BCD4)),
        Tag(name = "Sem Histamina", color = Color(0xFF00BCD4)),
        Tag(name = "Sem Lactose", color = Color(0xFF00BCD4)),
    )  ),

    Filter("Alérgicos", listOf(
        Tag(name = "Amendoim", color = Color(0xFFF44336)),
        Tag(name = "Castanhas", color = Color(0xFFF44336)),
        Tag(name = "Gergelim", color = Color(0xFFF44336)),
        Tag(name = "Frutos do Mar", color = Color(0xFFF44336)),
        Tag(name = "Leite", color = Color(0xFFF44336)),
        Tag(name = "Nozes", color = Color(0xFFF44336)),
        Tag(name = "Ovos", color = Color(0xFFF44336)),
        Tag(name = "Soja", color = Color(0xFFF44336)),
    )  ),

    Filter("Tipo de Refeição", listOf(
        Tag(name = "Almoço", color = Color(0xFFFF9700)),
        Tag(name = "Café da Manhã", color = Color(0xFFFF9700)),
        Tag(name = "Jantar", color = Color(0xFFFF9700)),
        Tag(name = "Lanchinho", color = Color(0xFFFF9700)),
        Tag(name = "Petisco", color = Color(0xFFFF9700)),
        Tag(name = "Sobremesa", color = Color(0xFFFF9700)),
    )  ),

    Filter("Facilidade", listOf(
        Tag(name = "Em 10 Minutos", color = Color(0xFFE91E63)),
        Tag(name = "Em 20 Minutos", color = Color(0xFFE91E63)),
        Tag(name = "Feito na Airfrayer", color = Color(0xFFE91E63)),
        Tag(name = "Ideal para Marmita", color = Color(0xFFE91E63)),
        Tag(name = "Micro-ondas", color = Color(0xFFE91E63)),
        Tag(name = "Rápido", color = Color(0xFFE91E63)),
        Tag(name = "Sem Batedeira", color = Color(0xFFE91E63)),
        Tag(name = "Sem Fogão", color = Color(0xFFE91E63)),
        Tag(name = "Sem Forno", color = Color(0xFFE91E63)),
        Tag(name = "Sem Fritar", color = Color(0xFFE91E63)),
        Tag(name = "Uma Panela Só", color = Color(0xFFE91E63)),
    )  ),
)