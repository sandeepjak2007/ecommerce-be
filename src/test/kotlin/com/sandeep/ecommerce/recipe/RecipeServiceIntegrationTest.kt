package com.sandeep.ecommerce.recipe

import com.sandeep.ecommerce.DBUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import kotlin.test.BeforeTest
import kotlin.test.Test

@SpringBootTest
class RecipeServiceIntegrationTest {

    @Autowired
    private lateinit var recipeService: RecipeService

    @Autowired
    private lateinit var recipeRepository: RecipeRepository


    @Test
    fun `getAllRecipes should return all recipes as RecipeDTO`() {
        val recipe = DBUtil.recipes[0]
        recipe.id = null
        recipeRepository.save(recipe)

        val recipes = recipeService.getAllRecipes()

        assertEquals(1, recipes.size)
        assertEquals(DBUtil.recipes[0].name, recipes[0].name)
        assertEquals(DBUtil.recipes[0].totalPriceInCents, recipes[0].totalPriceInCents)
    }

    @Test
    @Rollback
    fun `getAllRecipes should return an empty list when no recipes exist`() {
        val recipes = recipeService.getAllRecipes()
        assertEquals(0, recipes.size)
    }

}