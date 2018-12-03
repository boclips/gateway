package com.boclips.api.gateway.config

import com.boclips.api.gateway.application.RequestExtractor
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order

@Configuration
class RoutesConfig {

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder, routingProperties: RoutingProperties): RouteLocator {
        return builder.routes()
                .route { p ->
                    p.path("/v1/marketing-collections/**")
                            .uri(routingProperties.marketingServiceUrl)
                }
                .route { p ->
                    p.path("/v1/jobs/**")
                            .uri(routingProperties.videoIngestorUrl)
                }
                .route { p ->
                    p.path("/v1/users/**")
                            .uri(routingProperties.userServiceUrl)
                }

                .build()
    }

    @Order(1)
    @Bean
    fun forwardedHeadersFilter(): GlobalFilter {

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
