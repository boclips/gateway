package com.boclips.api.gateway.config

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

    @Bean
    fun corsWebFilter() = CorsWebFilter(UrlBasedCorsConfigurationSource().apply {
        registerCorsConfiguration("/**", CorsConfiguration().apply {
            allowedOrigins = listOf(
                    "http://localhost:8080",
                    "http://localhost:8081",
                    "http://localhost:3000",

                    "https://teachers.testing-boclips.com",
                    "https://teachers.staging-boclips.com",
                    "https://teachers.oldvic-boclips.com",
                    "https://teachers.boclips.com",

                    "https://backoffice.testing-boclips.com",
                    "https://backoffice.staging-boclips.com",
                    "https://backoffice.boclips.com",

                    "https://publishers.testing-boclips.com",
                    "https://publishers.staging-boclips.com",
                    "https://publishers.boclips.com",

                    "https://api.testing-boclips.com",
                    "https://api.staging-boclips.com",
                    "https://api.oldvic-boclips.com",
                    "https://api.boclips.com",

                    "https://lti.testing-boclips.com",
                    "https://lti.staging-boclips.com",
                    "https://lti.boclips.com",

                    "https://login.testing-boclips.com",
                    "https://login.staging-boclips.com",
                    "https://login.boclips.com",

                    "https://testing-boclips.com",
                    "https://staging-boclips.com",
                    "https://boclips.com",

                    "https://www.testing-boclips.com",
                    "https://www.staging-boclips.com",
                    "https://www.boclips.com",

                    "https://myviewboard.com",
                    "https://myviewclip.myviewboard.cloud",
                    "https://stage.myviewboard.com",
                    "https://myviewclip.stage.myviewboard.cloud",
                    "https://myviewboardclips.com",

                    "http://nuadu-system-www-2.0:8000",
                    "https://test-k8s-system.nuadu.com",
                    "https://test2-k8s-system.nuadu.com",
                    "https://test3-k8s-system.nuadu.com",
                    "https://app.nuadu.com",
                    "https://app.nuadu.pl",

                    "https://local.lessonplanet.com",
                    "https://local.lessonplanet.com:3000",
                    "https://staging.lessonplanet.com",
                    "https://feature.lessonplanet.com",
                    "https://lessonplanet.com",
                    "https://www.lessonplanet.com",

                    "https://read.kortext.com",
                    "https://read.dev.kortext.com",
                    "https://read.qa.kortext.com",
                    "https://read.uat.kortext.com",
                    "https://read-api.kortext.com",
                    "https://read-api.dev.kortext.com",
                    "https://read-api.qa.kortext.com",
                    "https://read-api.uat.kortext.com",

                    "http://generalonologicsoft.com", // This is for them to test the integration, but they don't seem to have a proper staging environment...

                    "http://testnew.empass.mobi",
                    "https://app.learntinue.ai",

                    "https://lighthousereader.com",
                    "https://lightsailed.com",
                    "https://reader.lightsailed.com"
            )
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
