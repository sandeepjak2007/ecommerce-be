package com.sandeep.ecommerce.recipe

import com.sandeep.ecommerce.model.RecipeProduct
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeProductRepository: JpaRepository<RecipeProduct, Int>