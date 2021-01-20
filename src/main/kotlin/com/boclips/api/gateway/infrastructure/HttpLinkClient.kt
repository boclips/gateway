package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.domain.model.RequestDomain
import mu.KLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.net.URI

@Component
class HttpLinkClient {
    companion object : KLogging()
    val noLinks = Links(_links = emptyMap())

    fun fetch(uri: URI, requestDomain: RequestDomain): Mono<Links> {
        val webClient = WebClient.builder().baseUrl(uri.toString()).build()

        return webClient.get()
                .uri("/v1/")
                .headers {
                    requestDomain.headers.entries.forEach { h -> it.set(h.key, h.value) }

                    it.set("X-Forwarded-Host", requestDomain.host)
                    it.set("X-Forwarded-Port", requestDomain.port.toString())
                    it.set("X-Forwarded-Proto", requestDomain.protocol)
                }
                .exchange()
                .flatMap {
                    if (it.statusCode().isError) {
                        logger.info("Unable to fetch links from uri=$uri, status=${it.statusCode()}")
                        Mono.just(noLinks)
                    } else {
                        it.bodyToMono(Links::class.java)
                    }
                }
                .defaultIfEmpty(noLinks)
    }
}
