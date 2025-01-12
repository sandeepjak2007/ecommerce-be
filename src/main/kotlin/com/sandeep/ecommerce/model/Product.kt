package com.sandeep.ecommerce.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "products")
data class Product(
    @Id
    @SequenceGenerator(
        name = "product_sequence",
        sequenceName = "product_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "product_sequence"
    )
    val id: Int? = null,
    @Column(nullable = false)

    val name: String,

    @Column(name = "price_in_cents")
    val priceInCents: Int
)