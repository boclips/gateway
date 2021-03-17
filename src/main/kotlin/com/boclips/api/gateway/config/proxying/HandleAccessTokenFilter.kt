package com.boclips.api.gateway.config.proxying

import com.boclips.api.gateway.application.user.HandleAccessToken
import com.boclips.api.gateway.config.TokenResponse
import mu.KLogging
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class HandleAccessTokenFilter(
    val handleAccessToken: HandleAccessToken
) {

    operator fun invoke(body: TokenResponse): Mono<TokenResponse> {
        return Mono.fromCallable {
            body.access_token?.let {
                handleAccessToken(it)
            }
            body
        }.subscribeOn(Schedulers.elastic())
    }

    companion object: KLogging()
}
