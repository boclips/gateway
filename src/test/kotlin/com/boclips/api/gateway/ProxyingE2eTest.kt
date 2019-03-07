package com.boclips.api.gateway

import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.matching.AnythingPattern
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.getForObject


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
    fun `users are proxied to user-service`() {
        userServiceMock.register(get(urlEqualTo("/v1/users"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/users", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `users sub-resources are proxied to user-service`() {
        userServiceMock.register(get(urlEqualTo("/v1/users/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/users/1", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `videos are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/videos"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/videos", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `collections are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/collections"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/collections", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `videos sub-resources are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/videos/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/videos/1", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `events are proxied to event-service`() {
        eventServiceMock.register(get(urlEqualTo("/v1/events"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello-from-event-service"))
        )

        val response = restTemplate.getForObject("/v1/events", String::class.java)
        assertThat(response).isEqualTo("hello-from-event-service")
    }

    @Test
    fun `(legacy) playback events are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/events/playback"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello-from-video-service"))
        )

        val response = restTemplate.getForObject("/v1/events/playback", String::class.java)
        assertThat(response).isEqualTo("hello-from-video-service")
    }

    @Test
    fun `(legacy) no search results events are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/events/no-search-results"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello-from-video-service"))
        )

        val response = restTemplate.getForObject("/v1/events/no-search-results", String::class.java)
        assertThat(response).isEqualTo("hello-from-video-service")
    }

    @Test
    fun `token requests are proxied to keycloak`() {
        keycloakMock.register(get(urlEqualTo("/auth/realms/boclips/protocol/openid-connect/token"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/token", String::class.java)
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

    @Test
    fun `gateway request strips origin header if present`() {
        marketingServiceMock.register(get(urlEqualTo("/v1/marketing-collections"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withBody(""))
        )

        val headers = HttpHeaders().apply { this.origin = "https://publishers.boclips.com" }
        val entity = HttpEntity(null, headers)
        restTemplate.exchange("/v1/marketing-collections", HttpMethod.GET, entity, String::class.java)

        marketingServiceMock.verifyThat(getRequestedFor(urlEqualTo("/v1/marketing-collections"))
                .withoutHeader("origin")
        )
    }

    @Test
    fun `analytics events are proxied to mixpanel`() {
        mixpanelMock.register(get(urlEqualTo("/track?data=abc"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json")
                .withBody("0"))
        )

        val response = restTemplate.getForObject("/v1/mp/track?data=abc", String::class.java)

        assertThat(response).isEqualTo("0")
    }

}