package com.boclips.api.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig {
    @Bean
    fun corsWebFilter() = CorsWebFilter(UrlBasedCorsConfigurationSource().apply {
        registerCorsConfiguration("/**", CorsConfiguration().apply {
            allowedOrigins = listOf(
                    "http://localhost:8080",
                    "http://localhost:8081",
                    "http://localhost:3000",
                    "https://educators.staging-boclips.com",
                    "https://educators.testing-boclips.com",
                    "https://educators.boclips.com"
            )
            allowedMethods = listOf("GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS")
            addAllowedHeader("Baeldung-Allowed")
        })
    })
}