package com.boclips.api.gateway.config

import com.boclips.api.gateway.application.RequestExtractor
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order

@Configuration
class ForwardedHeaderGatewayConfig {

    @Order(1)
    @Bean
    fun forwardedHeaderGateway(): GlobalFilter {

        return GlobalFilter { exchange, chain ->
            val requestDomain = RequestExtractor().extract(exchange.request)
            val request = exchange.request.mutate()
                    .headers { httpHeaders ->
                        httpHeaders.remove("Forwarded")
                        httpHeaders.add("x-forwarded-host", requestDomain.host)
                        httpHeaders.add("x-forwarded-port", requestDomain.port.toString())
                        httpHeaders.add("x-forwarded-proto", requestDomain.protocol)
                    }
                    .build()

            chain.filter(exchange.mutate().request(request).build())
        }
    }
}