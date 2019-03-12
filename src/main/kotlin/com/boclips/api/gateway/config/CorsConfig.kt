package com.boclips.api.gateway.config

import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import reactor.core.publisher.Mono

@Configuration
class CorsConfig {

    @Bean
    fun corsWebFilter() = CorsWebFilter(UrlBasedCorsConfigurationSource().apply {
        registerCorsConfiguration("/**", CorsConfiguration().apply {
            allowedOrigins = listOf(
                    "http://localhost:8080",
                    "http://localhost:8081",
                    "http://localhost:3000",
                    "https://teachers.testing-boclips.com",
                    "https://teachers.staging-boclips.com",
                    "https://teachers.boclips.com",
                    "https://publishers.boclips.com",
                    "https://boclips.com",
                    "https://login.testing-boclips.com",
                    "https://login.staging-boclips.com",
                    "https://login.boclips.com"
            )
            allowedMethods = listOf("*")
            allowedHeaders = listOf("*")
            allowCredentials = true
        })
    })

    @Bean
    fun enforceGatewayAllowedOriginsFilter(): GlobalFilter {
        return GlobalFilter { exchange, chain ->
            val allowedOrigins: MutableList<String>? = exchange.response.headers["Access-Control-Allow-Origin"]

            chain.filter(exchange).then(Mono.defer {
                allowedOrigins?.let {
                    exchange.response.headers["Access-Control-Allow-Origin"] = it
                }

                Mono.empty<Void>()
            })
        }
    }
}