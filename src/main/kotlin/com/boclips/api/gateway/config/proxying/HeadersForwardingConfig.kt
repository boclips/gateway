package com.boclips.api.gateway.config.proxying

import com.boclips.api.gateway.application.RequestExtractor
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order

@Configuration
class HeadersForwardingConfig {

    @Order(1)
    @Bean
    fun forwardedHeadersFilter(): GlobalFilter {

        return GlobalFilter { exchange, chain ->
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

            chain.filter(exchange.mutate().request(request).build())
        }
    }
}
