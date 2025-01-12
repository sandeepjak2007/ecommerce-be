package com.sandeep.ecommerce.cart

import com.sandeep.ecommerce.model.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface CartItemRepository : JpaRepository<CartItem, Int> {

    @Query("SELECT * FROM cart_items WHERE cart_id=?1", nativeQuery = true)
    fun getItemFromCartId(cartId: Int): Optional<List<CartItem>>

    @Query("SELECT * FROM cart_items WHERE recipe_id=?1", nativeQuery = true)
    fun getItemFromRecipeId(recipeId: Int): Optional<CartItem>

    @Transactional
    @Modifying
    @Query("DELETE FROM cart_items WHERE cart_id=?1 AND recipe_id=?2", nativeQuery = true)
    fun deleteRecipeFromACart(cartId: Int, recipeId: Int): Optional<Boolean>
}