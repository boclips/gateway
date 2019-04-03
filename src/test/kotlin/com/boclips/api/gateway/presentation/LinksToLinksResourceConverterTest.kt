package com.boclips.api.gateway.presentation

import com.boclips.api.gateway.domain.model.Link
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import reactor.core.publisher.Flux

class LinksToLinksResourceConverterTest {

    @Test
    fun `templated links formats using HAL guidelines`() {
        val linksToLinksResourceConverter = LinksToLinksResourceConverter()

        val resource = linksToLinksResourceConverter.convert(Flux.fromIterable(listOf(Link(href = "http://example.com", templated = true, rel = "myResource")))).block()

        assertThat(resource).isEqualTo(LinksResource(
                mapOf(
                        "myResource" to mapOf(
                                "href" to "http://example.com",
                                "templated" to true
                        )
                )
        ))
    }

    @Test
    fun `links formats using HAL guidelines`() {
        val linksToLinksResourceConverter = LinksToLinksResourceConverter()

        val resource = linksToLinksResourceConverter.convert(Flux.fromIterable(listOf(Link(href = "http://example.com", rel = "myResource")))).block()

        assertThat(resource).isEqualTo(LinksResource(
                mapOf(
                        "myResource" to mapOf(
                                "href" to "http://example.com",
                                "templated" to false
                        )
                )
        ))
    }

    @Test
    fun `if existing rel ignores second element`() {
        val linksToLinksResourceConverter = LinksToLinksResourceConverter()

        assertThat(
            linksToLinksResourceConverter.convert(Flux.fromIterable(listOf(
                    Link(href = "http://example.com", rel = "myResource"),
                    Link(href = "http://example.com", rel = "myResource")
            ))).block()
        ).isEqualTo(LinksResource(
                mapOf(
                        "myResource" to mapOf(
                                "href" to "http://example.com",
                                "templated" to false
                        )
                )
        ))

    }
}