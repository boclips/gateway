package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.domain.service.TraceProvider
import mu.KLogging
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class CustomGlobalLogger(private val traceProvider: TraceProvider) : WebFilter {
    companion object : KLogging()

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return chain.filter(exchange).doOnRequest { _ ->
            logger.info {
                "${exchange.request.methodValue} request for ${exchange.request.uri} with trace id: ${traceProvider.getTraceId()?.value} gave status: ${
                    exchange.response.statusCode
                }"
            }
        }
    }
}

