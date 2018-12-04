package com.boclips.api.gateway.config

import com.boclips.api.gateway.config.links.CustomerFacing
import com.boclips.api.gateway.infrastructure.HttpLinkClient
import com.boclips.api.gateway.infrastructure.HttpLinkRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoriesConfig(
        val httpLinkClient: HttpLinkClient,
        val routingProperties: RoutingProperties
) {

    @Bean
    fun marketingServiceLinkRepository() = HttpLinkRepository(
            httpLinkClient,
            routingProperties.marketingServiceUrl
    )

    @Bean
    fun videoIngestorLinkRepository() = HttpLinkRepository(
            httpLinkClient,
            routingProperties.videoIngestorUrl
    )

    @Bean
    @CustomerFacing
    fun userServiceLinkRepository() = HttpLinkRepository(
            httpLinkClient,
            routingProperties.userServiceUrl
    )

    @Bean
    @CustomerFacing
    fun videoServiceLinkRepository() = HttpLinkRepository(
            httpLinkClient,
            routingProperties.videoServiceUrl
    )

}