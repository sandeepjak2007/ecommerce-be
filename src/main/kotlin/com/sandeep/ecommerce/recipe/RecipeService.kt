package com.sandeep.ecommerce.recipe

import com.sandeep.ecommerce.model.RecipeDTO
import com.sandeep.ecommerce.model.toDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RecipeService {

    @Autowired
    private lateinit var repository: RecipeRepository

    fun getAllRecipes(): List<RecipeDTO> {
        val list = repository.findAll()
        return if (list.isEmpty()) {
            emptyList()
        } else {
            list.map { it.toDTO() }
        }
    }

}