package com.boclips.api.gateway

import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


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
    fun `gateway request contains X-Forwarded-Host header`() {
        marketingServiceMock.register(get(urlEqualTo("/v1/marketing-collections"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withBody(""))
        )

        restTemplate.getForObject("/v1/marketing-collections", String::class.java)
        marketingServiceMock.verifyThat(getRequestedFor(urlEqualTo("/v1/marketing-collections"))
                .withHeader("X-Forwarded-Host", equalTo("localhost:$appPort")))
    }

}