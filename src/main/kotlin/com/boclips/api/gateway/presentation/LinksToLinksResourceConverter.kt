package com.boclips.api.gateway.presentation

import com.boclips.api.gateway.domain.model.Link
import org.springframework.stereotype.Service

@Service
class LinksToLinksResourceConverter {

    fun convert(links: List<Link>): LinksResource {
        val linkEntries = mutableMapOf<String, Map<String, Any>>()
        links.forEach {
            if(!linkEntries[it.rel].isNullOrEmpty()){
                throw DuplicateRelException(
                        "The hypermedia link with rel=${it.rel} appears twice " +
                                "for links $it and ${linkEntries[it.rel]}")
            }
            linkEntries[it.rel] = mapOf(
                    "href" to it.href,
                    "templated" to it.templated
            )
        }
        return LinksResource(linkEntries)
    }

}