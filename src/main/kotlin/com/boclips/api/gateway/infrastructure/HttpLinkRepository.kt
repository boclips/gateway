package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.domain.model.Link
import com.boclips.api.gateway.domain.model.LinkRepository
import com.boclips.api.gateway.domain.model.RequestDomain
import java.net.URI

class HttpLinkRepository(
        private val httpLinkClient: HttpLinkClient,
        private val serviceUri: String
) : LinkRepository {

    override fun findAll(requestDomain: RequestDomain): List<Link> {
        val links = httpLinkClient.fetch(URI(serviceUri), requestDomain)

        return links._links.entries.map {
            Link(
                    href = it.value["href"] as String,
                    templated = it.value["templated"] as? Boolean ?: false,
                    rel = it.key)
        }
    }
}