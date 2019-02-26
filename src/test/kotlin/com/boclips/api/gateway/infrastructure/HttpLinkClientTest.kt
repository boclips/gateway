package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.domain.model.RequestDomain
import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.net.URI

internal class HttpLinkClientTest : AbstractSpringIntegrationTest() {

    @Autowired
    lateinit var httpLinkClient: HttpLinkClient

    @Test
    fun `when empty body returns no links`() {
        marketingServiceMock.register(get(urlEqualTo("/v1/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                ))

        val links = httpLinkClient.fetch(
                URI(marketingServiceWireMockServer.url("")),
                RequestDomain(protocol = "http", host = "example.com", port = 80)
        )

        Assertions.assertThat(links._links).isEmpty()
    }

    @Test
    fun `when server error return no links`() {
        marketingServiceMock.register(get(urlEqualTo("/v1/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withStatus(500)
                ))

        val links = httpLinkClient.fetch(
                URI(marketingServiceWireMockServer.url("")),
                RequestDomain(protocol = "http", host = "example.com", port = 80)
        )

        Assertions.assertThat(links._links).isEmpty()
    }

    @Test
    fun `set X-Forwarded headers`() {
        marketingServiceMock.register(get(urlEqualTo("/v1/"))
                .willReturn(aResponse()))

        httpLinkClient.fetch(
                URI(marketingServiceWireMockServer.url("")),
                RequestDomain(protocol = "https", host = "example.com", port = 80)
        )

        marketingServiceMock.verifyThat(getRequestedFor(urlEqualTo("/v1/"))
                .withHeader("X-Forwarded-Host", equalTo("example.com"))
                .withHeader("X-Forwarded-Port", equalTo("80"))
                .withHeader("X-Forwarded-Proto", equalTo("https"))
        )
    }

    @Test
    fun `sets Authorization headers`() {
        marketingServiceMock.register(get(urlEqualTo("/v1/"))
                .willReturn(aResponse()))

        httpLinkClient.fetch(
                URI(marketingServiceWireMockServer.url("")),
                RequestDomain(protocol = "https", host = "example.com", port = 80, headers = mapOf("Authorization" to "poke me in the coconut"))
        )

        marketingServiceMock.verifyThat(getRequestedFor(urlEqualTo("/v1/"))
                .withHeader("X-Forwarded-Host", equalTo("example.com"))
                .withHeader("X-Forwarded-Port", equalTo("80"))
                .withHeader("X-Forwarded-Proto", equalTo("https"))
                .withHeader("Authorization", equalTo("poke me in the coconut"))
        )
    }
}