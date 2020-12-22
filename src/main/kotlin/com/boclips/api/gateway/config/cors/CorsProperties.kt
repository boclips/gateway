package com.boclips.api.gateway.config.cors

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("gateway.cors")
class CorsProperties {
    lateinit var allowedOriginsTemp: List<String>
}
