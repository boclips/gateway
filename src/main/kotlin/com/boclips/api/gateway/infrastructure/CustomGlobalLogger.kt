package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.domain.service.TraceProvider
import mu.KLogging
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class CustomGlobalLogger(private val traceProvider: TraceProvider) : GlobalFilter, Ordered {
    companion object : KLogging()

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        return chain.filter(exchange).doFinally { _ ->
            logger.info {
                "${exchange.request.methodValue} request for ${exchange.request.uri} with trace id: ${traceProvider.getTraceId()?.value} gave status: ${
                    exchange.response.statusCode
                }"
            }
        }
    }

    override fun getOrder(): Int {
        return Int.MIN_VALUE
    }
}

