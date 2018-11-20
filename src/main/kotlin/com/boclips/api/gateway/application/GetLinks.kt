package com.boclips.api.gateway.application

import com.boclips.api.gateway.domain.model.LinkRepository
import com.boclips.api.gateway.presentation.LinksResource
import com.boclips.api.gateway.presentation.LinksToLinksResourceConverter
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

@Service
class GetLinks(
        val linksResourceConverter: LinksToLinksResourceConverter,
        val linkRepositories: Collection<LinkRepository>
) {

    val requestExtractor = RequestExtractor()
    fun execute(uri: UriComponentsBuilder, serverHttpRequest: ServerHttpRequest): LinksResource {
        return linksResourceConverter.convert(


                linkRepositories.flatMap { repo ->
                    repo.findAll(requestExtractor.extract(serverHttpRequest))
                }
        )
    }
}