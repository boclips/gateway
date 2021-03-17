package com.boclips.api.gateway.config.proxying

import com.boclips.api.gateway.application.user.HandleAccessTokenCustomBoclipsClaims
import com.boclips.api.gateway.config.TokenResponse
import mu.KLogging
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class HandleAccessTokenFilter(
    val handleAccessTokenCustomBoclipsClaims: HandleAccessTokenCustomBoclipsClaims
) {

    operator fun invoke(body: TokenResponse): Mono<TokenResponse> {
        return Mono.fromCallable {
            body.access_token?.let {
                handleAccessTokenCustomBoclipsClaims(it)
            }
            body
        }.subscribeOn(Schedulers.boundedElastic())
    }

    companion object: KLogging()
}
