package com.boclips.api.gateway.config.proxying

import com.boclips.api.gateway.config.TokenResponse
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.stereotype.Component

@Component
class HandleAccessTokenFilterFactory(
    val filter: HandleAccessTokenFilter
) {

    fun get(gatewayFilterSpec: GatewayFilterSpec): GatewayFilterSpec? {
        return gatewayFilterSpec.modifyResponseBody(TOKEN, TOKEN) { _, body -> filter(body) }
    }

    companion object {

        val TOKEN = TokenResponse::class.java
    }
}
