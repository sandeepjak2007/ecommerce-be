package com.sandeep.ecommerce.cart

import com.sandeep.ecommerce.model.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart, Int>