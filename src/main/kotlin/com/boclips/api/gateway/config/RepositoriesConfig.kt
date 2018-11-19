package com.boclips.api.gateway.config

import com.boclips.api.gateway.infrastructure.HttpLinkRepository
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoriesConfig(
        val restTemplateBuilder: RestTemplateBuilder,
        val routingProperties: RoutingProperties
) {

    @Bean
    fun marketingServiceLinkRepository() = HttpLinkRepository(
            restTemplateBuilder,
            routingProperties.marketingServiceUrl
    )

    @Bean
    fun videoIngestorLinkRepository() = HttpLinkRepository(
            restTemplateBuilder,
            routingProperties.videoIngestorUrl
    )

}