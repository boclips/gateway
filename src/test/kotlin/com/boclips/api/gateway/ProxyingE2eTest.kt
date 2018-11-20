package com.boclips.api.gateway

import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.matching.AnythingPattern
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod


class ProxyingE2eTest : AbstractSpringIntegrationTest() {

    @Test
    fun `marketing-collections are proxied to marketing-service`() {
        marketingServiceMock.register(get(urlEqualTo("/v1/marketing-collections"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/marketing-collections", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `marketing-collections sub-resource are proxied to marketing-service`() {
        marketingServiceMock.register(get(urlEqualTo("/v1/marketing-collections/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/marketing-collections/1", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `jobs are proxied to video-ingestor`() {
        videoIngestorMock.register(get(urlEqualTo("/v1/jobs"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/jobs", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `jobs sub-resource are proxied to video-ingestor`() {
        videoIngestorMock.register(get(urlEqualTo("/v1/jobs/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/jobs/1", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `gateway request propagates X-Forwarded-* headers when present`() {
        marketingServiceMock.register(get(urlEqualTo("/v1/marketing-collections"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withBody(""))
        )

        val headers = HttpHeaders().apply {
            set("X-Forwarded-Host", "example.com")
            set("X-Forwarded-Port", "443")
            set("X-Forwarded-Proto", "https")
        }
        val entity = HttpEntity(null, headers)
        restTemplate.exchange("/v1/marketing-collections", HttpMethod.GET, entity, String::class.java)

        marketingServiceMock.verifyThat(getRequestedFor(urlEqualTo("/v1/marketing-collections"))
                .withHeader("X-Forwarded-Host", equalTo("example.com"))
                .withHeader("X-Forwarded-Proto", equalTo("https"))
                .withHeader("X-Forwarded-Port", equalTo("443"))
        )
    }

    @Test
    fun `gateway request sets X-Forwarded-* headers when headers not present`() {
        marketingServiceMock.register(get(urlEqualTo("/v1/marketing-collections"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withBody(""))
        )

        val headers = HttpHeaders()
        val entity = HttpEntity(null, headers)
        restTemplate.exchange("/v1/marketing-collections", HttpMethod.GET, entity, String::class.java)

        marketingServiceMock.verifyThat(getRequestedFor(urlEqualTo("/v1/marketing-collections"))
                .withHeader("X-Forwarded-Host", equalTo("localhost"))
                .withHeader("X-Forwarded-Proto", equalTo("http"))
                .withHeader("X-Forwarded-Port", AnythingPattern())
        )
    }

}