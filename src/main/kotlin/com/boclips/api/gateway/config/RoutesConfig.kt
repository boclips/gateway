package com.boclips.api.gateway.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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

                .build()
    }
}
