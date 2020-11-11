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
    fun routeLocator(
        builder: RouteLocatorBuilder,
        props: RoutingProperties,
        handleAccessTokenFilterProducer: HandleAccessTokenFilterProducer
    ): RouteLocator {
        return builder.routes {
            route {
                path("/v1/http-feeds/**")
                uri(props.videoIngestorUrl)
            }
            route {
                path("/v1/jobs/**")
                uri(props.videoIngestorUrl)
            }
            route {
                path("/v1/ingest-videos/**")
                uri(props.videoIngestorUrl)
            }
            route {
                path("/v1/ingests/**")
                uri(props.videoIngestorUrl)
            }
            route {
                path("/v1/ingest-video-statuses")
                uri(props.videoIngestorUrl)
            }
            route {
                path("/v1/users/**/collections")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/users/**")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/api-users/**")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/e2e-users/**")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/countries/**")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/schools/**")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/access-rules/**")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/selected-content-access-rules/**")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/api-integrations/**")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/organisations/**")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/content-packages/**")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/accounts/**")
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
                path("/v1/disciplines/**")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/tags/**")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/attachment-types")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/video-types")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/age-ranges")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/suggestions")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/content-warnings")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/events/playback")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/events/video-visited")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/events/player-interaction")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/events/no-search-results")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/events/page-render")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/events/platform-interaction")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/events/expired-user-access")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/integrations/**")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/events/playback/batch")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/events/suggested-search-completions")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/distribution-methods/**")
                uri(props.videoServiceUrl)
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
                path("/v1/channels/**")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/contracts/**")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/marketing-statuses/**")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/legal-restrictions/**")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/contract-legal-restrictions")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/content-categories/**")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/admin/actions/**")
                uri(props.videoServiceUrl)
            }
            route {
                path("/v1/admin/users/actions/**")
                uri(props.userServiceUrl)
            }
            route {
                path("/v1/admin/orders/actions/**")
                uri(props.orderServiceUrl)
            }
            route {
                path("/v1/token")
                filters {
                    rewritePath("/v1/token", RETRIEVE_TOKEN_PATH)
                    handleAccessTokenFilterProducer.get(this)
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
