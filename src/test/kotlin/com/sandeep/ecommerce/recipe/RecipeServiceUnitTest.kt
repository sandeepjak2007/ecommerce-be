package com.sandeep.ecommerce.recipe

import com.sandeep.ecommerce.DBUtil.recipes
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class RecipeServiceUnitTest {

    @InjectMocks
    private lateinit var recipeService: RecipeService

    @Mock
    private lateinit var recipeRepository: RecipeRepository


    @Test
    fun `getAllRecipes should return list of RecipeDTO`() {
        `when`(recipeRepository.findAll()).thenReturn(recipes)

        val result = recipeService.getAllRecipes()
        assertEquals(recipes.size, result.size)
        assertEquals(recipes[0].name, result[0].name)
        assertEquals(recipes[0].totalPriceInCents, result[0].totalPriceInCents)
        verify(recipeRepository, times(1)).findAll()
    }

    @Test
    fun `getAllRecipes should return empty list`() {
        `when`(recipeRepository.findAll()).thenReturn(emptyList())
        val result = recipeService.getAllRecipes()
        assertEquals(emptyList(), result)
        assertEquals(0, result.size)
        verify(recipeRepository, times(1)).findAll()
    }

}