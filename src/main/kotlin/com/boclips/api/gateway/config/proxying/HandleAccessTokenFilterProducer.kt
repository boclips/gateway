package com.boclips.api.gateway.config.proxying

import com.boclips.api.gateway.application.user.HandleAccessToken
import com.boclips.api.gateway.config.TokenResponse
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class HandleAccessTokenFilterProducer(
    val handleAccessToken: HandleAccessToken
) {
    fun get(gatewayFilterSpec: GatewayFilterSpec): GatewayFilterSpec? {
        return gatewayFilterSpec.modifyResponseBody(
            TokenResponse::class.java, TokenResponse::class.java
        ) { _: ServerWebExchange, body: TokenResponse ->
            Mono.fromCallable {
                body.access_token?.let {
                    handleAccessToken(it)
                }
                body
            }.subscribeOn(Schedulers.elastic())
        }
    }
}
