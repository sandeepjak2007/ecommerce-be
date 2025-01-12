package com.sandeep.ecommerce.recipe

import com.sandeep.ecommerce.model.Recipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Repository
interface RecipeRepository : JpaRepository<Recipe, Int>