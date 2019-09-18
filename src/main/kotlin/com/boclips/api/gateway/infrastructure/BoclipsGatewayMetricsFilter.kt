package com.boclips.api.gateway.infrastructure

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class BoclipsGatewayMetricsFilter(private val meterRegistry: MeterRegistry) : WebFilter, Ordered {

    override fun getOrder(): Int {
        return 0
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain) =
            chain.filter(exchange).doOnSuccessOrError { _, _ ->
                exchange.request.headers["Authorization"]?.firstOrNull()?.removePrefix("Bearer ")?.let { token ->
                    safeDecode(token)?.getClaim("azp")?.asString()?.let { authorizedParty ->
                        meterRegistry.counter(
                                "boclips.api-usage",
                                listOf(
                                        Tag.of("url", exchange.request.uri.toASCIIString()),
                                        Tag.of("client-id", authorizedParty)
                                )
                        ).increment()
                    }
                }
            }

    fun safeDecode(token: String) : DecodedJWT? = try {
        JWT.decode(token)
    } catch (e: Exception) {
        null
    }
}

