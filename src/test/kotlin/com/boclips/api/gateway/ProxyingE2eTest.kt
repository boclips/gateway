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
    fun `http-feeds are proxied to video-ingestor`() {
        videoIngestorMock.register(get(urlEqualTo("/v1/http-feeds"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/http-feeds", String::class.java)
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
    fun `countries are proxied to user-service`() {
        userServiceMock.register(get(urlEqualTo("/v1/countries"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/countries", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `US states are proxied to user-service`() {
        userServiceMock.register(get(urlEqualTo("/v1/countries/USA/states"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/countries/USA/states", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `schools are proxied to user-service`() {
        userServiceMock.register(get(urlEqualTo("/v1/schools"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/schools", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `contracts are proxied to User Service`() {
        userServiceMock.register(get(urlEqualTo("/v1/contracts"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/contracts", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `organisations are proxied to User Service`() {
        userServiceMock.register(get(urlEqualTo("/v1/organisations"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/organisations", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `api-integrations are proxied to User Service`() {
        userServiceMock.register(get(urlEqualTo("/v1/api-integrations"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/api-integrations", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `content partners are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/content-partners"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/content-partners", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `content partner sub-resources are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/content-partners/1"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/content-partners/1", String::class.java)
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
    fun `subjects are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/subjects"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/subjects", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `disciplines are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/disciplines"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/disciplines", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `tags are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/tags"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/tags", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `orders are proxied to order-service`() {
        orderServiceMock.register(get(urlEqualTo("/v1/orders"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("hello-from-order-service")))

        val response = restTemplate.getForObject("/v1/orders", String::class.java)
        assertThat(response).isEqualTo("hello-from-order-service")
    }

    @Test
    fun `orders sub-resources are proxied to order-service`() {
        orderServiceMock.register(get(urlEqualTo("/v1/orders/1"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/orders/1", String::class.java)
        assertThat(response).isEqualTo("hello")
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
    fun `(legacy) player interaction events are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/events/player-interaction"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("hello-from-video-service"))
        )

        val response = restTemplate.getForObject("/v1/events/player-interaction", String::class.java)
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
    fun `distribution-methods are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/distribution-methods"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("hello-from-video-service"))
        )

        val response = restTemplate.getForObject("/v1/distribution-methods", String::class.java)
        assertThat(response).isEqualTo("hello-from-video-service")
    }

    @Test
    fun `admin actions are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/admin/actions"))
            .willReturn(aResponse()
                .withHeader("content-Type", "text/plain")
                .withBody("hello-from-the-other-side-lol-jk-im-the-video-service"))
        )

        val response = restTemplate.getForObject("/v1/admin/actions", String::class.java)
        assertThat(response).isEqualTo("hello-from-the-other-side-lol-jk-im-the-video-service")
    }

    @Test
    fun `admin action sub resources are proxied to video-service`() {
        videoServiceMock.register(get(urlEqualTo("/v1/admin/actions/whatever"))
            .willReturn(aResponse()
                .withHeader("content-Type", "text/plain")
                .withBody("hello-from-the-other-side-lol-jk-im-the-video-service"))
        )

        val response = restTemplate.getForObject("/v1/admin/actions/whatever", String::class.java)
        assertThat(response).isEqualTo("hello-from-the-other-side-lol-jk-im-the-video-service")
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
    fun `authorize requests are proxied to keycloak`() {
        keycloakMock.register(get(urlEqualTo("/auth/realms/boclips/protocol/openid-connect/auth"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("hello"))
        )

        val response = restTemplate.getForObject("/v1/authorize", String::class.java)
        assertThat(response).isEqualTo("hello")
    }

    @Test
    fun `gateway request propagates X-Forwarded-* headers when present`() {
        videoIngestorMock.register(get(urlEqualTo("/v1/http-feeds/foo"))
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
        restTemplate.exchange("/v1/http-feeds/foo", HttpMethod.GET, entity, String::class.java)

        videoIngestorMock.verifyThat(getRequestedFor(urlEqualTo("/v1/http-feeds/foo"))
            .withHeader("X-Forwarded-Host", equalTo("example.com"))
            .withHeader("X-Forwarded-Proto", equalTo("https"))
            .withHeader("X-Forwarded-Port", equalTo("443"))
        )
    }

    @Test
    fun `gateway request sets X-Forwarded-* headers when headers not present`() {
        videoIngestorMock.register(get(urlEqualTo("/v1/http-feeds/foo"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/hal+json")
                .withBody(""))
        )

        val headers = HttpHeaders()
        val entity = HttpEntity(null, headers)
        restTemplate.exchange("/v1/http-feeds/foo", HttpMethod.GET, entity, String::class.java)

        videoIngestorMock.verifyThat(getRequestedFor(urlEqualTo("/v1/http-feeds/foo"))
            .withHeader("X-Forwarded-Host", equalTo("localhost"))
            .withHeader("X-Forwarded-Proto", equalTo("http"))
            .withHeader("X-Forwarded-Port", AnythingPattern())
        )
    }

    @Test
    fun `gateway request strips origin header if present`() {
        videoIngestorMock.register(get(urlEqualTo("/v1/http-feeds/foo"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/hal+json")
                .withBody(""))
        )

        val headers = HttpHeaders().apply { this.origin = "https://publishers.boclips.com" }
        val entity = HttpEntity(null, headers)
        restTemplate.exchange("/v1/http-feeds/foo", HttpMethod.GET, entity, String::class.java)

        videoIngestorMock.verifyThat(getRequestedFor(urlEqualTo("/v1/http-feeds/foo"))
            .withoutHeader("origin")
        )
    }

    @Test
    fun `requests get origin headers rewritten`() {
        videoIngestorMock.register(get(urlEqualTo("/v1/http-feeds/foo"))
            .willReturn(aResponse()
                .withHeader("Access-Control-Allow-Origin", "*")
                .withBody(""))
        )

        val headers = HttpHeaders()
        headers.add("Origin", "https://login.boclips.com")
        val response = restTemplate.exchange<String>("/v1/http-feeds/foo", HttpMethod.GET, HttpEntity(null, headers), String::class.java)

        val allowedOrigins = response.headers["Access-Control-Allow-Origin"]

        assertThat(allowedOrigins).contains("https://login.boclips.com")
        assertThat(allowedOrigins).doesNotContain("*")
    }

}
