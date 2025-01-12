package com.sandeep.ecommerce.cart

import com.sandeep.ecommerce.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Int>