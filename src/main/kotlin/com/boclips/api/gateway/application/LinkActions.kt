package com.boclips.api.gateway.application

import com.boclips.api.gateway.config.links.CustomerFacing
import com.boclips.api.gateway.domain.model.LinkRepository
import com.boclips.api.gateway.presentation.LinksResource
import com.boclips.api.gateway.presentation.LinksToLinksResourceConverter
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class LinkActions(
        val linksResourceConverter: LinksToLinksResourceConverter,
        val requestExtractor: RequestExtractor,
        val allRepositories: Collection<LinkRepository>,
        @CustomerFacing
        val customerFacingRepositories: Collection<LinkRepository>
) {

    fun getAllLinks(serverHttpRequest: ServerHttpRequest): Mono<LinksResource> {
        return linksResourceConverter.convert(
                Flux.fromIterable(allRepositories).flatMap { repo ->
                    repo.findAll(requestExtractor.extract(serverHttpRequest))
                }
        )
    }

    fun getCustomerFacingLinks(serverHttpRequest: ServerHttpRequest): Mono<LinksResource> {
        return linksResourceConverter.convert(
                Flux.fromIterable(customerFacingRepositories).flatMap { repo ->
                    repo.findAll(requestExtractor.extract(serverHttpRequest))
                }
        )
    }
}