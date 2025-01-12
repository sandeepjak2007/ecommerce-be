package com.sandeep.ecommerce.cart

import com.sandeep.ecommerce.DBUtil
import com.sandeep.ecommerce.model.Cart
import com.sandeep.ecommerce.model.RecipeDTO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class CartControllerUnitTest {

    @InjectMocks
    private lateinit var cartController: CartController

    @Mock
    private lateinit var cartService: CartService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `getCartById should return single cart when id is passed successfully`() {
        val cart = DBUtil.carts[0]
        `when`(cartService.getCartById(1)).thenReturn(cart)

        val response: ResponseEntity<Cart> = cartController.getCartById(1)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(cart, response.body)
        assertEquals(cart.id, response.body?.id)
        assertEquals(cart.totalInCents, response.body?.totalInCents)
    }

    @Test
    fun `getCartById should throw INTERNAL SERVER ERROR when no cart is present with passed id`() {
        val id = DBUtil.carts[0].id
        `when`(cartService.getCartById(id!!)).thenThrow(RuntimeException("Cart is not present in db with id $id"))
        val response: ResponseEntity<Cart> = cartController.getCartById(id)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(null, response.body)
    }

    @Test
    fun `getCartById should throw NOT FOUND when no cart is present with passed id`() {
        val id = DBUtil.carts[0].id
        `when`(cartService.getCartById(id!!)).thenThrow(IllegalStateException("Cart is not present in db with id $id"))
        val response: ResponseEntity<Cart> = cartController.getCartById(id)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertEquals(null, response.body)
    }

    @Test
    fun `addRecipeToACart should return success with CREATED`() {
        val cartId = DBUtil.carts[0].id
        val recipeDTO = RecipeDTO(
            name = DBUtil.recipes[0].name,
            recipeId = DBUtil.recipes[0].id,
            totalPriceInCents = DBUtil.recipes[0].totalPriceInCents
        )
        val response: ResponseEntity<String> = cartController.addRecipeToACart(cartId!!, recipeDTO)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals("Added recipe successfully for cart having id $cartId", response.body)
    }

    @Test
    fun `addRecipeToACart should return recipe name shouldn't be empty`() {
        val cartId = 1
        val recipeDTO = RecipeDTO(
            name = "",
            recipeId = DBUtil.recipes[0].id,
            totalPriceInCents = DBUtil.recipes[0].totalPriceInCents
        )
        val response: ResponseEntity<String> = cartController.addRecipeToACart(cartId, recipeDTO)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("Recipe Name should not be empty", response.body)

    }

    @Test
    fun `addRecipeToACart should return INTERNAL SERVER ERROR`() {
        val cartId = DBUtil.carts[0].id
        val recipeDTO = RecipeDTO(
            name = DBUtil.recipes[0].name,
            recipeId = DBUtil.recipes[0].id,
            totalPriceInCents = DBUtil.recipes[0].totalPriceInCents
        )
        `when`(cartService.addRecipeToACart(cartId!!, recipeDTO)).thenThrow(RuntimeException("Unknown error"))

        val response: ResponseEntity<String> = cartController.addRecipeToACart(cartId, recipeDTO)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(null, response.body)
    }

    @Test
    fun `deleteRecipeFromACart should return success with Ok`() {
        val cartId = DBUtil.carts[0].id!!
        val recipeId = DBUtil.recipes[0].id!!
        val response: ResponseEntity<String> = cartController.deleteRecipeFromACart(cartId, recipeId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(
            "Recipe from a cart is deleted successfully having cartId $cartId & recipeId $recipeId",
            response.body
        )
    }

    @Test
    fun `deleteRecipeFromACart should return INTERNAL SERVER ERROR`() {
        val cartId = 1
        val recipeId = 1
        `when`(cartService.deleteRecipeFromACart(cartId, recipeId)).thenThrow(RuntimeException("Unknown error"))

        val response: ResponseEntity<String> = cartController.deleteRecipeFromACart(cartId, recipeId)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals(null, response.body)
    }

}