package com.boclips.api.gateway

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Routes {

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()

                .route { p ->
                    p
                            .path("/marketing-service/**")
                            .filters{f->f.rewritePath("/marketing-service/(?<segment>.*)","/api/v1/\${segment}")}
                            .uri("https://backoffice.boclips.com")
                }
                .build()
    }
}
