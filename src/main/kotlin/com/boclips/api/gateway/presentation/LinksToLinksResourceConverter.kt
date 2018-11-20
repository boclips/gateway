package com.boclips.api.gateway.presentation

import com.boclips.api.gateway.domain.model.Link
import org.springframework.stereotype.Service

@Service
class LinksToLinksResourceConverter {

    fun convert(links: List<Link>): LinksResource {
        val linkEntries = mutableMapOf<String, Map<String, Any>>()
        links.forEach {
            linkEntries[it.rel] = mapOf(
                    "href" to it.href,
                    "templated" to it.templated
            )
        }
        return LinksResource(linkEntries)
    }

}