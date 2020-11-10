package com.boclips.api.gateway.config.proxying

import com.boclips.api.gateway.application.user.CreateApiUserFromToken
import com.boclips.api.gateway.config.TokenResponse
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class CreateApiUserFilterProducer(
    val createApiUserFromToken: CreateApiUserFromToken
) {

    fun get(gatewayFilterSpec: GatewayFilterSpec): GatewayFilterSpec? {
        return gatewayFilterSpec
            .modifyResponseBody(TokenResponse::class.java, TokenResponse::class.java) { _: ServerWebExchange, body: TokenResponse ->
                createApiUserFromToken(body.access_token)
                Mono.just(body)
            }
    }
}
