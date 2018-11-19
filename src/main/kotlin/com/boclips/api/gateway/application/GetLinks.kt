package com.boclips.api.gateway.application

import com.boclips.api.gateway.domain.model.LinkRepository
import com.boclips.api.gateway.presentation.LinksResource
import com.boclips.api.gateway.presentation.LinksToLinksResourceConverter
import org.springframework.stereotype.Service

@Service
class GetLinks(
        val linksResourceConverter: LinksToLinksResourceConverter,
        val linkRepositories: Collection<LinkRepository>
) {

    fun execute(uri: String): LinksResource = linksResourceConverter.convert(
            linkRepositories.flatMap { repo ->
                repo.findAll().map { it.replaceDomain(uri) } }
    )
}