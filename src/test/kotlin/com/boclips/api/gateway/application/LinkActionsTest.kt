package com.boclips.api.gateway.application

import com.boclips.api.gateway.domain.model.Link
import com.boclips.api.gateway.domain.model.LinkRepository
import com.boclips.api.gateway.domain.model.RequestDomain
import com.boclips.api.gateway.presentation.LinksToLinksResourceConverter
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.server.reactive.ServerHttpRequest
import reactor.core.publisher.Flux

class LinkActionsTest {
    private val requestExtractorMock = Mockito.mock(RequestExtractor::class.java)
    private val serverHttpRequest = Mockito.mock(ServerHttpRequest::class.java)

    @Test
    fun `getAllLinks aggregates links from all existing repositories`() {
        whenever(requestExtractorMock.extract(any())).thenReturn(RequestDomain("", "", 0))
        val subject = LinkActions(
                LinksToLinksResourceConverter(),
                requestExtractorMock,
                listOf(dummyRepository("1"), dummyRepository("2")),
                listOf(dummyRepository("2"))
        )

        val links = subject.getAllLinks(serverHttpRequest)
        assertThat(links.block()!!._links).hasSize(2)
    }

    @Test
    fun `getCustomerFacingLinks aggregates links from customer facing repositories`() {
        whenever(requestExtractorMock.extract(any())).thenReturn(RequestDomain("", "", 0))
        val subject = LinkActions(
                LinksToLinksResourceConverter(),
                requestExtractorMock,
                listOf(dummyRepository("1"), dummyRepository("2")),
                listOf(dummyRepository("2"))
        )

        val links = subject.getCustomerFacingLinks(serverHttpRequest)
        assertThat(links.block()!!._links).hasSize(1)
    }

    fun dummyRepository(linkPath: String) = object : LinkRepository {
        override fun findAll(requestDomain: RequestDomain) =
                Flux.fromIterable(listOf(Link("http://example.com/$linkPath", false, "link-$linkPath")))

    }
}

