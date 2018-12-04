package com.boclips.api.gateway.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoutesConfig {

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder, props: RoutingProperties): RouteLocator {
        return builder.routes()
                .route {
                    it.path("/v1/marketing-collections/**")
                    it.uri(props.marketingServiceUrl)
                }
                .route {
                    it.path("/v1/jobs/**")
                    it.uri(props.videoIngestorUrl)
                }
                .route {
                    it.path("/v1/users/**")
                    it.uri(props.userServiceUrl)
                }
                .route {
                    it.path("/v1/videos/**")
                    it.uri(props.videoServiceUrl)
                }
                .route {
                    it.path("/v1/events/**")
                    it.uri(props.videoServiceUrl)
                }
                .build()
    }
}
