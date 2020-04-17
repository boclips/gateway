package com.boclips.api.gateway

import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.matching.AnythingPattern
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod

class ProxyingE2eTest : AbstractSpringIntegrationTest() {
    @Nested
    inner class VideoIngestorProxies {
        @Test
        fun `jobs are proxied to video-ingestor`() {
            videoIngestorMock.register(
                get(urlEqualTo("/v1/jobs"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/jobs", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `ingest videos are proxied to video-ingestor`() {
            videoIngestorMock.register(
                get(urlEqualTo("/v1/ingest-videos"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/ingest-videos", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `http-feeds are proxied to video-ingestor`() {
            videoIngestorMock.register(
                get(urlEqualTo("/v1/http-feeds"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/http-feeds", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `ingests are proxied to video-ingestor`() {
            videoIngestorMock.register(
                get(urlEqualTo("/v1/ingests"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/ingests", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `jobs sub-resource are proxied to video-ingestor`() {
            videoIngestorMock.register(
                get(urlEqualTo("/v1/jobs/1"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/jobs/1", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `ingest video statuses are proxied to the video-ingestor`() {
            videoIngestorMock.register(
                get(urlEqualTo("/v1/ingest-video-statuses"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("Hello, It's me, I was wondering if after all these years you'd like to meet")
                    )
            )

            val response = restTemplate.getForObject("/v1/ingest-video-statuses", String::class.java)
            assertThat(response).isEqualTo("Hello, It's me, I was wondering if after all these years you'd like to meet")
        }
    }

    @Nested
    inner class UserServiceProxies {
        @Test
        fun `selected content access rules are proxied to user-service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/selected-content-access-rules"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/selected-content-access-rules", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `users are proxied to user-service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/users"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/users", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `users sub-resources are proxied to user-service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/users/1"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/users/1", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `countries are proxied to user-service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/countries"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/countries", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `US states are proxied to user-service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/countries/USA/states"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/countries/USA/states", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `schools are proxied to user-service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/schools"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/schools", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `access rules are proxied to User Service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/access-rules"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/access-rules", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `organisations are proxied to User Service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/organisations"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/organisations", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `content-packages are proxied to User Service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/content-packages"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/content-packages", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `api-integrations are proxied to User Service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/api-integrations"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/api-integrations", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `page rendered events are proxied to user-service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/events/page-render"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello-from-user-service")
                    )
            )

            val response = restTemplate.getForObject("/v1/events/page-render", String::class.java)
            assertThat(response).isEqualTo("hello-from-user-service")
        }

        @Test
        fun `platform interaction events are proxied to user-service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/events/platform-interaction"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello-from-user-service")
                    )
            )

            val response = restTemplate.getForObject("/v1/events/platform-interaction", String::class.java)
            assertThat(response).isEqualTo("hello-from-user-service")
        }
    }

    @Nested
    inner class VideoServiceProxies {
        @Test
        fun `content partner contracts are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/content-partner-contracts"))
                    .willReturn(aResponse().withHeader("Content-Type", "text/plain").withBody("hello"))
            )

            val response = restTemplate.getForObject("/v1/content-partner-contracts", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `content partner contract sub-resources are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/content-partner-contracts/1"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/content-partner-contracts/1", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `content partners are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/content-partners"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/content-partners", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `content partner sub-resources are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/content-partners/1"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/content-partners/1", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `marketing statuses are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/marketing-statuses"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/marketing-statuses", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `legal restrictions are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/legal-restrictions"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )
            val response = restTemplate.getForObject("/v1/legal-restrictions", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `videos are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/videos"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/videos", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `videos are proxied to age-ranges`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/age-ranges"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/age-ranges", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `collections are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/collections"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/collections", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `videos sub-resources are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/videos/1"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/videos/1", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `subjects are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/subjects"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/subjects", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `disciplines are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/disciplines"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/disciplines", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `tags are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/tags"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/tags", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `video types are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/video-types"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/video-types", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `(legacy) player interaction events are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/events/player-interaction"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello-from-video-service")
                    )
            )

            val response = restTemplate.getForObject("/v1/events/player-interaction", String::class.java)
            assertThat(response).isEqualTo("hello-from-video-service")
        }

        @Test
        fun `(legacy) no search results events are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/events/no-search-results"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello-from-video-service")
                    )
            )

            val response = restTemplate.getForObject("/v1/events/no-search-results", String::class.java)
            assertThat(response).isEqualTo("hello-from-video-service")
        }

        @Test
        fun `distribution-methods are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/distribution-methods"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello-from-video-service")
                    )
            )

            val response = restTemplate.getForObject("/v1/distribution-methods", String::class.java)
            assertThat(response).isEqualTo("hello-from-video-service")
        }

        @Test
        fun `admin actions are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/admin/actions"))
                    .willReturn(
                        aResponse()
                            .withHeader("content-Type", "text/plain")
                            .withBody("hello-from-the-other-side-lol-jk-im-the-video-service")
                    )
            )

            val response = restTemplate.getForObject("/v1/admin/actions", String::class.java)
            assertThat(response).isEqualTo("hello-from-the-other-side-lol-jk-im-the-video-service")
        }

        @Test
        fun `content categories are fetched from video service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/content-categories"))
                    .willReturn(
                        aResponse()
                            .withHeader("content-Type", "text/plain")
                            .withBody("test-content-categories")
                    )
            )

            val response = restTemplate.getForObject("/v1/content-categories", String::class.java)
            assertThat(response).isEqualTo("test-content-categories")
        }

        @Test
        fun `admin action sub resources are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/admin/actions/whatever"))
                    .willReturn(
                        aResponse()
                            .withHeader("content-Type", "text/plain")
                            .withBody("hello-from-the-other-side-lol-jk-im-the-video-service")
                    )
            )

            val response = restTemplate.getForObject("/v1/admin/actions/whatever", String::class.java)
            assertThat(response).isEqualTo("hello-from-the-other-side-lol-jk-im-the-video-service")
        }

        @Test
        fun `(legacy) playback events are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/events/playback"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello-from-video-service")
                    )
            )

            val response = restTemplate.getForObject("/v1/events/playback", String::class.java)
            assertThat(response).isEqualTo("hello-from-video-service")
        }
    }

    @Nested
    inner class OrderServiceProxies {
        @Test
        fun `orders are proxied to order-service`() {
            orderServiceMock.register(
                get(urlEqualTo("/v1/orders"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello-from-order-service")
                    )
            )

            val response = restTemplate.getForObject("/v1/orders", String::class.java)
            assertThat(response).isEqualTo("hello-from-order-service")
        }

        @Test
        fun `orders sub-resources are proxied to order-service`() {
            orderServiceMock.register(
                get(urlEqualTo("/v1/orders/1"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/orders/1", String::class.java)
            assertThat(response).isEqualTo("hello")
        }
    }

    @Nested
    inner class ContractLegalRestrictions {
        @Test
        fun `contract legal restrictions are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/contract-legal-restrictions"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello-from-restrictions")
                    )
            )

            val response = restTemplate.getForObject("/v1/contract-legal-restrictions", String::class.java)
            assertThat(response).isEqualTo("hello-from-restrictions")
        }
    }

    @Nested
    inner class KeycloakProxies {
        @Test
        fun `token requests are proxied to keycloak`() {
            keycloakMock.register(
                get(urlEqualTo("/auth/realms/boclips/protocol/openid-connect/token"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/token", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `authorize requests are proxied to keycloak`() {
            keycloakMock.register(
                get(urlEqualTo("/auth/realms/boclips/protocol/openid-connect/auth"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/authorize", String::class.java)
            assertThat(response).isEqualTo("hello")
        }
    }

    @Test
    fun `gateway accepts HTTPS and propagates X-Forwarded-* headers when present`() {
        videoIngestorMock.register(
            get(urlEqualTo("/v1/http-feeds/foo"))
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withBody("")
                )
        )

        val headers = HttpHeaders().apply {
            set("X-Forwarded-Host", "example.com")
            set("X-Forwarded-Port", "443")
            set("X-Forwarded-Proto", "https")
        }
        val entity = HttpEntity(null, headers)
        restTemplate.exchange("/v1/http-feeds/foo", HttpMethod.GET, entity, String::class.java)

        videoIngestorMock.verifyThat(
            getRequestedFor(urlEqualTo("/v1/http-feeds/foo"))
                .withHeader("X-Forwarded-Host", equalTo("example.com"))
                .withHeader("X-Forwarded-Proto", equalTo("https"))
                .withHeader("X-Forwarded-Port", equalTo("443"))
        )
    }

    @Test
    fun `gateway request sets X-Forwarded-* headers when headers not present`() {
        videoIngestorMock.register(
            get(urlEqualTo("/v1/http-feeds/foo"))
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withBody("")
                )
        )

        val entity = HttpEntity(null, HttpHeaders())
        restTemplate.exchange("/v1/http-feeds/foo", HttpMethod.GET, entity, String::class.java)

        videoIngestorMock.verifyThat(
            getRequestedFor(urlEqualTo("/v1/http-feeds/foo"))
                .withHeader("X-Forwarded-Host", equalTo("localhost"))
                .withHeader("X-Forwarded-Proto", equalTo("http"))
                .withHeader("X-Forwarded-Port", AnythingPattern())
        )
    }

    @Test
    fun `gateway request propagates arbitrary headers when present`() {
        videoIngestorMock.register(
            get(urlEqualTo("/v1/http-feeds/foo"))
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withHeader("Content-Disposition", """attachment; filename="i-like-rats.csv"""")
                        .withBody("")
                )
        )

        restTemplate.exchange("/v1/http-feeds/foo", HttpMethod.GET, HttpEntity(null, HttpHeaders()), String::class.java)
            .apply {
                assertThat(this.headers["Content-Type"]?.first()).isEqualTo("application/hal+json")
                assertThat(this.headers["Content-Disposition"]?.first()).isEqualTo("""attachment; filename="i-like-rats.csv"""")
                assertThat(this.headers["Access-Control-Expose-Headers"]!!).isEqualTo(listOf("*"))
            }
    }

    @Test
    fun `gateway request strips origin header if present`() {
        videoIngestorMock.register(
            get(urlEqualTo("/v1/http-feeds/foo"))
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withBody("")
                )
        )

        val headers = HttpHeaders().apply { this.origin = "https://publishers.boclips.com" }
        val entity = HttpEntity(null, headers)
        restTemplate.exchange("/v1/http-feeds/foo", HttpMethod.GET, entity, String::class.java)

        videoIngestorMock.verifyThat(
            getRequestedFor(urlEqualTo("/v1/http-feeds/foo"))
                .withoutHeader("origin")
        )
    }

    @Test
    fun `requests get origin headers rewritten`() {
        videoIngestorMock.register(
            get(urlEqualTo("/v1/http-feeds/foo"))
                .willReturn(
                    aResponse()
                        .withHeader("Access-Control-Allow-Origin", "*")
                        .withBody("")
                )
        )

        val headers = HttpHeaders()
        headers.add("Origin", "https://login.boclips.com")
        val response = restTemplate.exchange<String>(
            "/v1/http-feeds/foo",
            HttpMethod.GET,
            HttpEntity(null, headers),
            String::class.java
        )

        val allowedOrigins = response.headers["Access-Control-Allow-Origin"]

        assertThat(allowedOrigins).contains("https://login.boclips.com")
        assertThat(allowedOrigins).doesNotContain("*")
    }

    @Test
    fun `gateway returns redirect for x-forwarded-proto http`() {
        videoIngestorMock.register(get(urlEqualTo("/v1/http-feeds/foo")).willReturn(aResponse()))

        val httpClient: CloseableHttpClient = HttpClients.createMinimal()
        val request = HttpGet("http://localhost:${appPort}/v1/http-feeds/foo")
        request.addHeader("x-forwarded-proto", "http")

        val response: CloseableHttpResponse = httpClient.execute(request)

        assertThat(response.statusLine.statusCode).isEqualTo(301)
    }

    @Test
    fun `gateway does not redirect for x-forwarded-proto https`() {
        videoIngestorMock.register(get(urlEqualTo("/v1/http-feeds/foo")).willReturn(aResponse()))

        val entity = HttpEntity(null, HttpHeaders().apply {
            add("x-forwarded-proto", "https")
        })

        restTemplate.exchange("/v1/http-feeds/foo", HttpMethod.GET, entity, String::class.java)

        videoIngestorMock.verifyThat(getRequestedFor(urlEqualTo("/v1/http-feeds/foo")))
    }

    @Test
    fun `gateway does not redirect for x-forwarded-proto not set`() {
        videoIngestorMock.register(get(urlEqualTo("/v1/http-feeds/foo")).willReturn(aResponse()))

        val entity = HttpEntity(null, HttpHeaders())

        restTemplate.exchange("/v1/http-feeds/foo", HttpMethod.GET, entity, String::class.java)

        videoIngestorMock.verifyThat(getRequestedFor(urlEqualTo("/v1/http-feeds/foo")))
    }
}
