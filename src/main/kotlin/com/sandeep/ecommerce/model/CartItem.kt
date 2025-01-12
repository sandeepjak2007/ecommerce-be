package com.sandeep.ecommerce.model

import jakarta.persistence.*

@Entity
@Table(name = "cart_items")
data class CartItem(
    @Id
    @SequenceGenerator(
        name = "cart_items_sequence",
        sequenceName = "cart_items_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "cart_items_sequence"
    )
    var id: Int? = null,
    @Column(name = "cart_id")
    var cartId: Int,
    @Column(name = "product_id")
    val productId: Int = 0,
    @Column(name = "recipe_id")
    var recipeId: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "recipe_id", referencedColumnName = "id",
        insertable = false, updatable = false
    )
    val recipe: Recipe? = null,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(
        name = "cart_id", referencedColumnName = "id",
        insertable = false, updatable = false
    )
    val cart: Cart? = null,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(
        name = "product_id", referencedColumnName = "id",
        insertable = false, updatable = false
    )
    val product: Product? = null
)