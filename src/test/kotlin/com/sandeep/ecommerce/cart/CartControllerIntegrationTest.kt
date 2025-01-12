package com.sandeep.ecommerce.cart

import com.sandeep.ecommerce.DBUtil
import com.sandeep.ecommerce.model.RecipeDTO
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest
@AutoConfigureMockMvc
class CartControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var cartService: CartService

    @Test
    fun `getCartById should return cart when ID is valid`() {
        val cart = DBUtil.carts[0]

        `when`(cartService.getCartById(cart.id!!)).thenReturn(cart)

        mockMvc.perform(get("/carts/${cart.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(cart.id))
    }

    @Test
    fun `getCartById should return NOT_FOUND when cart does not exist`() {
        val cartId = 1

        `when`(cartService.getCartById(cartId)).thenThrow(IllegalStateException("Cart not found"))

        mockMvc.perform(get("/carts/$cartId"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `addRecipeToACart should add recipe successfully`() {
        val cartId = 1
        val recipe = RecipeDTO(name = "Tomato Soup", totalPriceInCents = 3)

        `when`(cartService.addRecipeToACart(cartId, recipe)).thenReturn(Unit)

        mockMvc.perform(
            post("/carts/$cartId/add_recipe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "name": "Tomato Kitchdi",
                        "products": ["Tomato", "Rice"]
                    }
                    """.trimIndent()
                )
        )
            .andExpect(status().isCreated)
            .andExpect(content().string("Added recipe successfully for cart having id $cartId"))
    }

    @Test
    fun `addRecipeToACart should return BAD_REQUEST for blank recipe name`() {
        val cartId = 1

        mockMvc.perform(
            post("/carts/$cartId/add_recipe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "name": "",
                        "products": ["Tomato", "Rice"]
                    }
                    """.trimIndent()
                )
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().string("Recipe Name should not be empty"))
    }

    @Test
    fun `deleteRecipeFromACart should delete recipe successfully`() {
        val cartId = 1
        val recipeId = 101

        `when`(cartService.deleteRecipeFromACart(cartId, recipeId)).thenReturn(Unit)

        mockMvc.perform(delete("/carts/$cartId/recipes/$recipeId"))
            .andExpect(status().isOk)
            .andExpect(content().string("Recipe from a cart is deleted successfully having cartId $cartId & recipeId $recipeId"))
    }

    @Test
    fun `deleteRecipeFromACart should return INTERNAL_SERVER_ERROR for exception`() {
        val cartId = 1
        val recipeId = 101

        `when`(cartService.deleteRecipeFromACart(cartId, recipeId)).thenThrow(RuntimeException("Database error"))

        mockMvc.perform(delete("/carts/$cartId/recipes/$recipeId"))
            .andExpect(status().isInternalServerError)
    }
}