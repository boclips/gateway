package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.application.JwtDecoder
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain

@Component
class BoclipsGatewayMetricsFilter(private val meterRegistry: MeterRegistry) : WebFilter, Ordered {

    override fun getOrder(): Int {
        return 0
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain) =
        chain.filter(exchange).doOnSuccessOrError { _, _ ->
            exchange.request.headers["Authorization"]?.firstOrNull()?.removePrefix("Bearer ")?.let { token ->
                JwtDecoder.safeDecode(token)?.getClaim("azp")?.asString()?.let { authorizedParty ->
                    meterRegistry.counter(
                        "boclips.api-usage",
                        listOf(
                            Tag.of("client-id", authorizedParty),
                            Tag.of(
                                "resource",
                                exchange.request.uri.toASCIIString()
                                    .substringAfter("/v1/")
                                    .substringBefore("?")
                                    .substringBefore("/")
                            )
                        )
                    ).increment()
                }
            }
        }
}
