package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.domain.service.TraceProvider
import mu.KLogging
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class TracingHeaderFilter(val traceProvider: TraceProvider) : WebFilter {
    companion object : KLogging()

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return chain.filter(
            exchange.mutate().response(addBoclipsTraceIdHeader(exchange)).build()
        )
    }

    private fun addBoclipsTraceIdHeader(exchange: ServerWebExchange): ServerHttpResponse {
        traceProvider.getTraceId()?.let {
            exchange.response.headers.set("X-Boclips-Trace-ID", it.value)
        }

        return exchange.response
    }
}
