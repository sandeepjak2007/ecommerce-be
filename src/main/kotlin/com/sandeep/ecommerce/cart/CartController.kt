package com.sandeep.ecommerce.cart

import com.sandeep.ecommerce.model.Cart
import com.sandeep.ecommerce.model.RecipeDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("carts")
class CartController {

    @Autowired
    private lateinit var cartService: CartService

    @GetMapping(path = ["{id}"])
    fun getCartById(@PathVariable("id") id: Int): ResponseEntity<Cart> {
        try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartService.getCartById(id))
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null)

        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null)

        }

    }

    @PostMapping(path = ["{id}/add_recipe"])
    fun addRecipeToACart(@PathVariable("id") cartId: Int, @RequestBody recipeDTO: RecipeDTO): ResponseEntity<String> {
        try {
            if (recipeDTO.name.isBlank()) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Recipe Name should not be empty")
            }
            cartService.addRecipeToACart(cartId, recipeDTO)
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Added recipe successfully for cart having id $cartId")
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null)
        }

    }

    @GetMapping(path = ["{id}/recipes/{rId}"])
    fun deleteRecipeFromACart(
        @PathVariable("id") cartId: Int,
        @PathVariable("rId") recipeId: Int
    ): ResponseEntity<String> {
        try {
            cartService.deleteRecipeFromACart(cartId, recipeId)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body("Recipe from a cart is deleted successfully having cartId $cartId & recipeId $recipeId")
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null)
        }
    }
}