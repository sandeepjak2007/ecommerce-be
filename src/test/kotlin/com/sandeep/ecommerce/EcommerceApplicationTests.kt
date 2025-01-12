package com.sandeep.ecommerce

import com.sandeep.ecommerce.cart.ProductRepository
import com.sandeep.ecommerce.recipe.RecipeRepository
import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EcommerceApplicationTests {

    @BeforeEach
    fun contextLoads() {
    }

}
