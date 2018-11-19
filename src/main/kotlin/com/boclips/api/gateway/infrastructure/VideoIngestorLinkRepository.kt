package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.config.RoutingProperties
import com.boclips.api.gateway.domain.model.Link
import com.boclips.api.gateway.domain.model.LinkRepository
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service

@Service
class VideoIngestorLinkRepository(
        restTemplateBuilder: RestTemplateBuilder,
        routingProperties: RoutingProperties
        ) : LinkRepository {

    val restTemplate = restTemplateBuilder.rootUri(routingProperties.videoIngestorUrl).build()

    override fun findAll(): List<Link> {
        val links: Links = restTemplate.getForObject("/v1/", Links::class.java)!!
        return links._links.entries.map { Link(
                href = it.value["href"] as String,
                templated = it.value["templated"] as? Boolean ?: false,
                rel = it.key) }
    }
}

