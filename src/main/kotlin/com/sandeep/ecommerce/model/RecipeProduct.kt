package com.sandeep.ecommerce.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "recipe_Product")
data class RecipeProduct(

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
    val id: Int? = null,

    @Column(name = "recipe_id")
    val recipeId: Int,

    @Column(name = "product_id")
    val productId: Int,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(
        name = "recipe_id", referencedColumnName = "id",
        insertable = false, updatable = false
    )
    val recipe: Recipe? = null,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(
        name = "product_id", referencedColumnName = "id",
        insertable = false, updatable = false
    )
    val product: Product? = null
)
