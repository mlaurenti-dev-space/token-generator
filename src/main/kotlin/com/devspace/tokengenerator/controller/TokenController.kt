package com.devspace.tokengenerator.controller

import com.devspace.tokengenerator.service.JwtTokenService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


data class TokenResponse(
    val accessToken: String
)

@RestController
@RequestMapping("/api/token")
@Tag(name = "Token")
class TokenController(private val tokenService: JwtTokenService) {

    @Operation(
        summary = "Generate JWT Token",
        description = "Generates a JWT token for the given subject with an optional expiration time.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Token generated successfully",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = TokenResponse::class)
                )]
            ),
            ApiResponse(responseCode = "400", description = "Invalid request")
        ]
    )
    @PostMapping
    fun generate(): Mono<TokenResponse> {
        val jwt = tokenService.generateToken()
        return Mono.just(TokenResponse(jwt))
    }
}