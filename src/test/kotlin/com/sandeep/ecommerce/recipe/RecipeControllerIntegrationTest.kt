package com.sandeep.ecommerce.recipe

import com.sandeep.ecommerce.DBUtil
import com.sandeep.ecommerce.cart.ProductRepository
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var recipeService: RecipeService

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var recipeRepository: RecipeRepository

    @Autowired
    private lateinit var recipeProductRepository: RecipeProductRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @BeforeEach
    fun save(){
        recipeProductRepository.deleteAll()
        entityManager.clear()
        recipeRepository.deleteAll()
        entityManager.clear()
        productRepository.deleteAll()
        entityManager.clear()
        productRepository.saveAll(DBUtil.products)
        entityManager.clear()
        recipeRepository.saveAll(DBUtil.recipes)
        entityManager.clear()
        recipeProductRepository.saveAll(DBUtil.recipeProducts)
    }

    @Test
    fun `getAllRecipes should return OK with list of recipes`() {

        val recipes = recipeService.getAllRecipes()

        mockMvc.perform(
            MockMvcRequestBuilders.get("/recipes")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(recipes.size))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(recipes[0].name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalPriceInCents").value(recipes[0].totalPriceInCents))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(recipes[1].name))
    }

    @Test
    fun `getAllRecipes should return INTERNAL_SERVER_ERROR on exception`() {

        `when`(recipeService.getAllRecipes()).thenThrow(RuntimeException("Unexpected error"))

        mockMvc.perform(
            MockMvcRequestBuilders.get("/recipes")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isInternalServerError)
    }
}