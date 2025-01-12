package com.sandeep.ecommerce.cart

import com.sandeep.ecommerce.DBUtil
import com.sandeep.ecommerce.recipe.RecipeRepository
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@DataJpaTest
class CartRepositoryUnitTest {

    @Autowired
    private lateinit var cartItemRepository: CartItemRepository

    @Autowired
    private lateinit var cartRepository: CartRepository

    @Autowired
    private lateinit var recipeRepository: RecipeRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @BeforeEach
    fun delete() {
        recipeRepository.deleteAll()
        cartRepository.deleteAll()
        cartRepository.deleteAll()
    }
    @Test
    fun `test getItemFromCartId returns items for a valid cartId`() {
        val cart = DBUtil.carts[0]
        val cartItem = DBUtil.cartItems[0]
        val recipe = DBUtil.recipes[0]
        recipe.id = null
        cartItem.id = null
        cart.id = null
        val newRecipe = recipeRepository.save(recipe)
        entityManager.clear()
        val newCart = cartRepository.save(cart)
        entityManager.clear()
        cartItem.recipeId = newRecipe.id!!
        cartItem.cartId = newCart.id!!
        val newCartItem = cartItemRepository.save(cartItem)

        val result = cartItemRepository.getItemFromCartId(newCartItem.cartId)

        assertTrue(result.isPresent)
        assertEquals(1, result.get().size)
        assertEquals(cartItem, result.get().first())
    }

    @Test
    fun `test getItemFromRecipeId returns item for a valid recipeId`() {

        val cartItem = DBUtil.cartItems[0]
        cartItemRepository.save(cartItem)

        val result = cartItemRepository.getItemFromRecipeId(cartItem.recipeId)

        assertTrue(result.isPresent)
        assertEquals(cartItem, result.get())
    }

    @Test
    @Transactional
    fun `test deleteRecipeFromACart deletes the item for valid cartId and recipeId`() {

        val cartItem = DBUtil.cartItems[0]
        cartItemRepository.save(cartItem)

        val result = cartItemRepository.deleteRecipeFromACart(cartItem.cartId, cartItem.recipeId)

        assertTrue(result.isPresent)
        assertTrue(result.get())
        assertFalse(cartItemRepository.findById(cartItem.id!!).isPresent)
    }
}