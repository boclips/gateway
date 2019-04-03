package com.boclips.api.gateway.presentation

import com.boclips.api.gateway.domain.model.Link
import mu.KLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class LinksToLinksResourceConverter {
    companion object : KLogging()

    fun convert(links: Flux<Link>) = links.reduceWith(
            { mutableMapOf<String, Map<String, Any>>() },
            { acc, link ->
                acc.apply {
                    if (this[link.rel].isNullOrEmpty()) {
                        this[link.rel] = mapOf(
                                "href" to link.href,
                                "templated" to link.templated
                        )
                    } else {
                        logger.error(
                                "The hypermedia link with rel=${link.rel} appears twice " +
                                        "for links $link and ${this[link.rel]}")
                    }
                }
            })
            .map { LinksResource(it) }

}
