package com.boclips.api.gateway.config

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsProcessor
import org.springframework.web.server.ServerWebExchange

class CloudCdnFriendlyCorsProcessor(private val next: CorsProcessor) : CorsProcessor {
    override fun process(configuration: CorsConfiguration?, exchange: ServerWebExchange): Boolean =
            next.process(configuration, exchange).also {
                if (exchange.request.method == GET) {
                    exchange.response.headers.vary = listOf(HttpHeaders.ORIGIN)
                }
            }
}
