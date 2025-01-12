package com.sandeep.ecommerce.cart

import com.sandeep.ecommerce.DBUtil
import com.sandeep.ecommerce.model.Cart
import com.sandeep.ecommerce.model.CartItem
import com.sandeep.ecommerce.model.RecipeDTO
import com.sandeep.ecommerce.recipe.RecipeRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class CartServiceUnitTest {

    @InjectMocks
    private lateinit var cartService: CartService

    @Mock
    private lateinit var cartRepository: CartRepository

    @Mock
    private lateinit var recipeRepository: RecipeRepository

    @Mock
    private lateinit var cartItemRepository: CartItemRepository

    @Test
    fun `getCartById return Single Cart with Ok`() {
        val cart = Cart(
            id = 1,
            totalInCents = 3
        )

        `when`(cartRepository.findById(1)).thenReturn(Optional.of(cart))

        val response = cartService.getCartById(1)
        assertEquals(cart, response)
        assertEquals(1, response.id)
        assertEquals(3, response.totalInCents)
        verify(cartRepository, times(1)).findById(1)
    }

    @Test
    fun `getCartById throws an Exception`() {
        val cartId = 1
        `when`(cartRepository.findById(cartId)).thenThrow(
            IllegalStateException(
                "Cart is not present in db with id $cartId"
            )
        )
        val exp = assertThrows<IllegalStateException> {
            cartService.getCartById(1)
        }
        assertEquals("Cart is not present in db with id $cartId", exp.message)
        assertEquals(exp::class.java.typeName, IllegalStateException::class.java.name)
    }

    @Test
    fun `addRecipeToACart runs successfully and returns single recipe object`() {
        val cart = DBUtil.carts[0]
        val recipe = DBUtil.recipes[0]

        `when`(cartRepository.findById(any())).thenReturn(Optional.of(cart))
        `when`(recipeRepository.findById(any())).thenReturn(Optional.of(recipe))

        cartService.addRecipeToACart(
            cartId = cart.id!!,
            recipeDTO = RecipeDTO(name = recipe.name, totalPriceInCents = recipe.totalPriceInCents, recipeId = recipe.id)
        )
        val argCartOption: ArgumentCaptor<Cart> = ArgumentCaptor.forClass(Cart::class.java)
        verify(cartRepository).save(argCartOption.capture())
        val capRecipe = argCartOption.value
        assertEquals(capRecipe.id, cart.id)

        val argOption: ArgumentCaptor<CartItem> = ArgumentCaptor.forClass(CartItem::class.java)
        verify(cartItemRepository).save(argOption.capture())
        val capItemRecipe = argOption.value
        assertEquals(capItemRecipe.recipeId, recipe.id)
    }

    @Test
    fun `addRecipeToACart runs and returns exception`() {
        val cart = DBUtil.carts[1]
        val recipe = DBUtil.recipes[3]
        `when`(cartRepository.findById(cart.id!!)).thenThrow(
            IllegalStateException(
                "Cart is not present in db with id ${cart.id}, So will not able to add recipe to a cart"
            )
        )
        val exp = assertThrows<IllegalStateException> {
            cartService.addRecipeToACart(
                cart.id!!,
                RecipeDTO(name = recipe.name, recipeId = recipe.id, totalPriceInCents = cart.totalInCents)
            )
        }
        assertEquals(
            "Cart is not present in db with id ${cart.id}, So will not able to add recipe to a cart",
            exp.message
        )
    }

    @Test
    fun `deleteRecipeFromACart runs successfully`() {
        val cart = DBUtil.carts[1]
        val cartItem = DBUtil.cartItems[2]
        val recipe = DBUtil.recipes[3]

        `when`(cartItemRepository.getItemFromCartId(cart.id!!)).thenReturn(Optional.of(listOf(cartItem)))
        `when`(cartItemRepository.getItemFromRecipeId(recipe.id!!)).thenReturn(Optional.of(cartItem))

        cartService.deleteRecipeFromACart(cartId = cart.id!!, recipeId = recipe.id!!)
        val newRecipe = cartItemRepository.findById(cartItem.id!!)
        assertEquals(newRecipe.isPresent, false)

    }

    @Test
    fun `deleteRecipeFromACart throws IllegalStateException for cart`() {
        val cartId = DBUtil.carts[0].id
        `when`(cartItemRepository.getItemFromCartId(cartId!!)).thenThrow(
            IllegalStateException(
                "Cart is not present in db with id $cartId, So will not able to delete recipe to a cart"
            )
        )
        val exp = assertThrows<IllegalStateException> {
            cartService.deleteRecipeFromACart(cartId, 1)
        }
        assertEquals(
            "Cart is not present in db with id $cartId, So will not able to delete recipe to a cart",
            exp.message
        )
        assertEquals(exp::class.java.typeName, IllegalStateException::class.java.name)
    }

    @Test
    fun `deleteRecipeFromACart throws IllegalStateException for recipe`() {
        val cart = DBUtil.carts[0]
        val cartItem = DBUtil.cartItems[0]
        `when`(cartItemRepository.getItemFromCartId(cart.id!!)).thenReturn(Optional.of(listOf(cartItem)))
        `when`(cartItemRepository.getItemFromRecipeId(cartItem.recipeId)).thenThrow(
            IllegalStateException(
                "Recipe is not present in db with id ${cart.id}, So will not able to delete recipe to a cart"
            )
        )

        val exp = assertThrows<IllegalStateException> {
            cartService.deleteRecipeFromACart(cart.id!!, cartItem.recipeId)
        }
        assertEquals(
            "Recipe is not present in db with id ${cart.id}, So will not able to delete recipe to a cart",
            exp.message
        )
        assertEquals(exp::class.java.typeName, IllegalStateException::class.java.name)
    }
}