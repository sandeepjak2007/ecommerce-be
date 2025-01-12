package com.sandeep.ecommerce.model

import jakarta.persistence.*

@Entity
@Table(name = "carts")
data class Cart(
    @Id
    @SequenceGenerator(
        name = "cart_sequence",
        sequenceName = "cart_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "cart_sequence"
    )
    var id: Int? = null,

    @Column(name = "total_in_cents")
    var totalInCents: Int,
)