package com.boclips.api.gateway.config.proxying

import com.boclips.api.gateway.application.RequestExtractor
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.net.URI
import java.net.URISyntaxException

@Configuration
class XForwardedHeadersConfiguration {

    @Bean
    fun httpsRedirectFilter(): WebFilter? {
        val fn = fun(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
            val originalUri: URI = exchange.request.uri
            val forwardedValues = exchange.request.headers["x-forwarded-proto"]
            return if (forwardedValues != null && forwardedValues.contains("http")) {
                try {
                    val mutatedUri = URI(
                        "https",
                        originalUri.userInfo,
                        originalUri.host,
                        originalUri.port,
                        originalUri.path,
                        originalUri.query,
                        originalUri.fragment
                    )
                    val response: ServerHttpResponse = exchange.response
                    response.statusCode = HttpStatus.MOVED_PERMANENTLY
                    response.headers.location = mutatedUri
                    Mono.empty<Void>()
                } catch (e: URISyntaxException) {
                    throw IllegalStateException(e.message, e)
                }
            } else chain.filter(exchange)
        }

        return WebFilter(fn)
    }

    @Order(1)
    @Bean
    fun forwardedHeadersFilter(): GlobalFilter {
        return GlobalFilter { exchange, chain ->
            setResponseHeaders(exchange)
            chain.filter(exchange.mutate().request(changeRequestHeaders(exchange)).build())
        }
    }

    private fun setResponseHeaders(exchange: ServerWebExchange) {
        exchange.response.headers["Access-Control-Expose-Headers"] = "*"
    }

    private fun changeRequestHeaders(exchange: ServerWebExchange): ServerHttpRequest {
        val requestDomain = RequestExtractor().extract(exchange.request)

        val request = exchange.request.mutate()
            .headers { headers ->
                headers.remove("Forwarded")
                headers.remove("x-forwarded-host")
                headers.remove("x-forwarded-port")
                headers.remove("x-forwarded-proto")
                headers.remove("origin")
                headers.add("x-forwarded-host", requestDomain.host)
                headers.add("x-forwarded-port", requestDomain.port.toString())
                headers.add("x-forwarded-proto", requestDomain.protocol)
            }
            .build()
        return request
    }
}
