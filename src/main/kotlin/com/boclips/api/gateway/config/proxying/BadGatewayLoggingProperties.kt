package com.boclips.api.gateway.config.proxying

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("gateway.bad_gateway")
class BadGatewayLoggingProperties {
    lateinit var videoIngestorUrl: String
    lateinit var userServiceUrl: String
    lateinit var videoServiceUrl: String
    lateinit var keycloakUrl: String
    lateinit var orderServiceUrl: String
}
