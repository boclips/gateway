package com.boclips.api.gateway.config.proxying

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RoutesConfig {
    companion object {
        const val RETRIEVE_TOKEN_PATH = "/auth/realms/teachers/protocol/openid-connect/token"
    }

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder, props: RoutingProperties): RouteLocator {
        return builder.routes {
            route {
                path("/v1/marketing-collections/**")
                uri(props.marketingServiceUrl)
            }
            route {
                path("/v1/jobs/**")
                uri(props.videoIngestorUrl)
            }
            route {
                path("/v1/users/**")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/videos/**")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/events/**")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/token")
                filters {
                    rewritePath("/v1/token", RETRIEVE_TOKEN_PATH)
                }
                uri("${props.keycloakUrl}")
            }
        }
    }
}
