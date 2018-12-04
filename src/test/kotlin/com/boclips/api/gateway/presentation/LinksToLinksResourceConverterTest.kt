package com.boclips.api.gateway.presentation

import com.boclips.api.gateway.domain.model.Link
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LinksToLinksResourceConverterTest {

    @Test
    fun `templated links formats using HAL guidelines`() {
        val linksToLinksResourceConverter = LinksToLinksResourceConverter()

        val resource = linksToLinksResourceConverter.convert(listOf(Link(href = "http://example.com", templated = true, rel = "myResource")))

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

        val resource = linksToLinksResourceConverter.convert(listOf(Link(href = "http://example.com", rel = "myResource")))

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
    fun `if existing rel throws exception`() {
        val linksToLinksResourceConverter = LinksToLinksResourceConverter()

        assertThrows<DuplicateRelException> {
            linksToLinksResourceConverter.convert(listOf(
                    Link(href = "http://example.com", rel = "myResource"),
                    Link(href = "http://example.com", rel = "myResource")
            ))
        }

    }
}