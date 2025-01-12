package com.sandeep.ecommerce.recipe

import com.sandeep.ecommerce.model.RecipeDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("recipes")
class RecipeController {

    @Autowired
    private lateinit var recipeService: RecipeService

    @GetMapping
    fun getAllRecipes(): ResponseEntity<List<RecipeDTO>> {
        try {
            val list = recipeService.getAllRecipes()
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(list)

        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null)
        }
    }
}