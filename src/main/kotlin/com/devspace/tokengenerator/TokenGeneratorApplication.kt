package com.devspace.tokengenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TokenGeneratorApplication

fun main(args: Array<String>) {
    runApplication<TokenGeneratorApplication>(*args)
}
