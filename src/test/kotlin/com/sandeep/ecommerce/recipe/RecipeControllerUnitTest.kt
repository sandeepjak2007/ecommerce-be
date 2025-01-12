package com.sandeep.ecommerce.recipe

import com.sandeep.ecommerce.DBUtil.recipes
import com.sandeep.ecommerce.model.RecipeDTO
import com.sandeep.ecommerce.model.toDTO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class RecipeControllerUnitTest {

    @InjectMocks
    private lateinit var recipeController: RecipeController

    @Mock
    private lateinit var recipeService: RecipeService

    @Test
    fun `getAllRecipes should return a list of recipes with Ok`() {
        `when`(recipeService.getAllRecipes()).thenReturn(recipes.map { it.toDTO() })

        val response: ResponseEntity<List<RecipeDTO>> = recipeController.getAllRecipes()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(recipes.map { it.toDTO() }, response.body)
    }

    @Test
    fun `getAllRecipes should return INTERNAL SERVER ERROR on exception`() {

        `when`(recipeService.getAllRecipes()).thenThrow(RuntimeException("Unexpected Error"))

        val response: ResponseEntity<List<RecipeDTO>> = recipeController.getAllRecipes()

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(null, response.body)
    }
}