package com.sandeep.ecommerce.cart

import com.sandeep.ecommerce.model.Cart
import com.sandeep.ecommerce.model.CartItem
import com.sandeep.ecommerce.model.RecipeDTO
import com.sandeep.ecommerce.recipe.RecipeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartService {

    @Autowired
    private lateinit var cartRepository: CartRepository

    @Autowired
    private lateinit var cartItemRepository: CartItemRepository

    @Autowired
    private lateinit var recipeRepository: RecipeRepository

    @Transactional
    fun getCartById(id: Int): Cart {
        val cart = cartRepository.findById(id)
            .orElseThrow {
                IllegalStateException(
                    "Cart is not present in db with id $id"
                )
            }
        return cart
    }

    @Transactional
    fun addRecipeToACart(cartId: Int, recipeDTO: RecipeDTO) {
        val cart = cartRepository.findById(cartId)
            .orElseThrow {
                IllegalStateException(
                    "Cart is not present in db with id $cartId, So will not able to add recipe to a cart"
                )
            }
        val recipe = recipeRepository.findById(recipeDTO.recipeId!!)
            .orElseThrow {
                IllegalStateException(
                    "Recipe is not present in db with id ${recipeDTO.recipeId}, So will not able to add recipe to a cart"
                )
            }
        cart.totalInCents += recipe.totalPriceInCents
        cartRepository.save(cart)
        cartItemRepository.save(
            CartItem(
                cartId = cart.id!!,
                recipeId = recipe.id!!
            )
        )
    }

    @Transactional
    fun deleteRecipeFromACart(cartId: Int, recipeId: Int) {
        cartItemRepository.getItemFromCartId(cartId)
            .orElseThrow {
                IllegalStateException(
                    "Cart is not present in db with id $cartId, So will not able to delete recipe to a cart"
                )
            }
        cartItemRepository.getItemFromRecipeId(recipeId)
            .orElseThrow {
                IllegalStateException(
                    "Recipe is not present in db with id $cartId, So will not able to delete recipe to a cart"
                )
            }
        cartItemRepository.deleteRecipeFromACart(cartId, recipeId)
    }
}