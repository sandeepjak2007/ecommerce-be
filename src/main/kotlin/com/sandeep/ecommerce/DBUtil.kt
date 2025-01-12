package com.sandeep.ecommerce

import com.sandeep.ecommerce.model.*

object DBUtil {
    val carts = listOf(
        Cart(1, 6),
        Cart(2,10)
    )

    val cartItems = listOf(
        CartItem(id = 1, cartId = 1, productId = 0, recipeId = 1),
        CartItem(id = 2, cartId = 1, productId = 0, recipeId = 2),
        CartItem(id = 3, cartId = 2, productId = 0, recipeId = 3),
        CartItem(id = 4, cartId = 2, productId = 0, recipeId = 4)
    )

    val products = listOf(
        Product(1, "Tomato",1),
        Product(2, "Moong Dal",3),
        Product(3, "Rasam Powder",2),
        Product(4, "Rice",4),
        Product(5, "Hing",6),
    )

    val recipes = listOf(
        Recipe(1, "Tomato Soupe", 3),
        Recipe(2, "Rasam", 3),
        Recipe(3, "Daal", 5),
        Recipe(4, "Palav", 5),
        Recipe(4, "Tomato Rice", 7),
    )

    val recipeProducts = listOf(
        RecipeProduct(1,1,1),
        RecipeProduct(2,1,3),
        RecipeProduct(3,2,1),
        RecipeProduct(4,2,3),
        RecipeProduct(5,3,2),
        RecipeProduct(6,3,1),
        RecipeProduct(7,4,4),
        RecipeProduct(8,4,1),
        RecipeProduct(9,5,1),
        RecipeProduct(10,5,5),
    )
}