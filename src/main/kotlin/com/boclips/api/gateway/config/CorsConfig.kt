package com.boclips.api.gateway.config

import com.boclips.api.gateway.config.cors.CorsProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.DefaultCorsProcessor
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import reactor.core.publisher.Mono

@Configuration
class CorsConfig {

    @Autowired
    lateinit var corsProperties: CorsProperties

    @Bean
    fun corsWebFilter() = CorsWebFilter(UrlBasedCorsConfigurationSource().apply {
        registerCorsConfiguration("/**", CorsConfiguration().apply {
            allowedOriginPatterns = corsProperties.allowedOrigins
            allowedMethods = listOf("*")
            allowedHeaders = listOf("*")
            allowCredentials = true
        })
    }, CloudCdnFriendlyCorsProcessor(DefaultCorsProcessor()))

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
