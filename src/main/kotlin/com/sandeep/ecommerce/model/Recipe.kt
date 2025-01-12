package com.sandeep.ecommerce.model

import jakarta.persistence.*

@Entity
@Table(name = "recipes")
data class Recipe(

    @Id
    @SequenceGenerator(
        name = "recipes_sequence",
        sequenceName = "recipes_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "recipes_sequence"
    )
    var id: Int? = null,
    val name: String,
    val totalPriceInCents: Int = 0

)

fun Recipe.toDTO() = RecipeDTO(id = this.id, name = this.name, totalPriceInCents = totalPriceInCents)
