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
        private const val REALM = "boclips"
        const val RETRIEVE_TOKEN_PATH = "/auth/realms/$REALM/protocol/openid-connect/token"
        const val AUTHORIZE_PATH = "/auth/realms/$REALM/protocol/openid-connect/auth"
    }

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder, props: RoutingProperties): RouteLocator {
        return builder.routes {
            route {
                path("/v1/marketing-collections/**")
                uri(props.marketingServiceUrl)
            }
            route {
                path("/v1/http-feeds/**")
                uri(props.videoIngestorUrl)
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
                path("/v1/subjects/**")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/events/playback")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/events/no-search-results")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/events")
                uri(props.eventServiceUrl)
            }
            route {
                path("/v1/collections/**")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/orders/**")
                uri(props.orderServiceUrl)
            }
            route {
                path("/v1/content-partners/**")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/token")
                filters {
                    rewritePath("/v1/token", RETRIEVE_TOKEN_PATH)
                }
                uri(props.keycloakUrl)
            }
            route {
                path("/v1/authorize")
                filters {
                    rewritePath("/v1/authorize", AUTHORIZE_PATH)
                }
                uri(props.keycloakUrl)
            }
        }
    }
}
