package com.sandeep.ecommerce.model


data class RecipeDTO(
    val id: Int? = null,
    val name: String,
    val recipeId: Int? = null,
    val totalPriceInCents: Int
)