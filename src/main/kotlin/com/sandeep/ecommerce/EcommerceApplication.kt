package com.sandeep.ecommerce

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping

@SpringBootApplication
class EcommerceApplication

fun main(args: Array<String>) {
    runApplication<EcommerceApplication>(*args)
}
