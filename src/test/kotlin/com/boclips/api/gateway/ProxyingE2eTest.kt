package com.boclips.api.gateway

import com.boclips.api.gateway.config.TokenResponse
import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.matching.AnythingPattern
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
        fun `e2e-users are proxied to user-service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/e2e-users"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/e2e-users", String::class.java)
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

        @Test
        fun `integrations proxied to user-service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/integrations"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello-from-user-service")
                    )
            )

            val response = restTemplate.getForObject("/v1/integrations", String::class.java)
            assertThat(response).isEqualTo("hello-from-user-service")
        }
    }

    @Nested
    inner class VideoServiceProxies {
        @Test
        fun `content partner contracts are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/contracts"))
                    .willReturn(aResponse().withHeader("Content-Type", "text/plain").withBody("hello"))
            )

            val response = restTemplate.getForObject("/v1/contracts", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `content partner contract sub-resources are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/contracts/1"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/contracts/1", String::class.java)
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
        fun `channels are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/channels"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/channels", String::class.java)
            assertThat(response).isEqualTo("hello")
        }

        @Test
        fun `channel sub-resources are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/channels/1"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/channels/1", String::class.java)
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
        fun `attachment types are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/attachment-types"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/attachment-types", String::class.java)
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
        fun `videos are proxied to suggestions`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/suggestions"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello")
                    )
            )

            val response = restTemplate.getForObject("/v1/suggestions", String::class.java)
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
        fun `user admin action sub resources are proxied to user-service`() {
            userServiceMock.register(
                get(urlEqualTo("/v1/admin/users/actions/whatever"))
                    .willReturn(
                        aResponse()
                            .withHeader("content-Type", "text/plain")
                            .withBody("response from the user service")
                    )
            )

            val response = restTemplate.getForObject("/v1/admin/users/actions/whatever", String::class.java)
            assertThat(response).isEqualTo("response from the user service")
        }

        @Test

        fun `order admin action sub resources are proxied to order-service`() {
            orderServiceMock.register(
                get(urlEqualTo("/v1/admin/orders/actions/anything"))
                    .willReturn(
                        aResponse()
                            .withHeader("content-Type", "text/plain")
                            .withBody("any response from order service")
                    )
            )
            val response = restTemplate.getForObject("/v1/admin/orders/actions/anything", String::class.java)
            assertThat(response).isEqualTo("any response from order service")
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

        @Test
        fun `collections of user are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/users/123/collections"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello-from-video-service")
                    )
            )

            val response = restTemplate.getForObject("/v1/users/123/collections", String::class.java)
            assertThat(response).isEqualTo("hello-from-video-service")
        }

        @Test
        fun `search query suggestions events are proxied to the video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/videos/suggested-search-completions"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello-from-video-service")
                    )
            )

            val response = restTemplate.getForObject("/v1/videos/suggested-search-completions", String::class.java)
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
                            .withHeader("Content-Type", "text/json")
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
    inner class ContentWarnings {
        @Test
        fun `content warnings are proxied to video-service`() {
            videoServiceMock.register(
                get(urlEqualTo("/v1/content-warnings"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "text/plain")
                            .withBody("hello-from-video-service")
                    )
            )

            val response = restTemplate.getForObject("/v1/content-warnings", String::class.java)
            assertThat(response).isEqualTo("hello-from-video-service")
        }
    }

    @Nested
    inner class KeycloakProxies {
        val token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJEVzg1cWVGSGp3dG9UN0Z2RkRwajJWelJyZTJRT3dsc" +
            "2JPNHQ1blNWQXpFIn0.eyJleHAiOjE2MDUwMjQ2NDMsImlhdCI6MTYwNTAyNDM0MywiYXV0aF90aW1lIjoxNjA0OTk3Mzg1LCJqdG" +
            "kiOiIyZDM2NzlkMi1iMjQ5LTQzYjktYWMxMC1iOTg2YzhlMzRkNjgiLCJpc3MiOiJodHRwczovL2xvZ2luLnN0YWdpbmctYm9jbGl" +
            "wcy5jb20vYXV0aC9yZWFsbXMvYm9jbGlwcyIsImF1ZCI6WyJ2aWRlby1pbmdlc3RvciIsInZpZGVvLXNlcnZpY2UiLCJvcmRlci1z" +
            "ZXJ2aWNlIiwidXNlci1zZXJ2aWNlIiwiYWNjb3VudCJdLCJzdWIiOiJiNjZmNmY5OC0zYzViLTQ5ZTMtYWMxYi0yZThkZWY2Yzk1Y" +
            "zAiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0ZWFjaGVycyIsIm5vbmNlIjoiMmI3MjdkNmEtN2M1MS00YjQ1LThhODEtZmY3YzNmZj" +
            "czOTRjIiwic2Vzc2lvbl9zdGF0ZSI6IjcyNWM5ZGU0LWFjNGMtNGZlYy05NGQ4LTg1NjhlZjgxNzQ5NCIsImFjciI6IjAiLCJhbGx" +
            "vd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly90ZWFjaGVycy5zdGFnaW5nLWJvY2xpcHMuY29tIiwiaHR0cDovL2xvY2FsaG9zdDo4MDgx" +
            "IiwiaHR0cDovL2JvdGh5LmxvY2FsOjgwODEiLCJodHRwOi8vMTAuMC4yLjI6ODA4MSJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiO" +
            "lsiUk9MRV9URUFDSEVSIiwiUk9MRV9IUSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InZpZGVvLWluZ2VzdG9yIjp7InJvbGVzIjpbIl" +
            "JPTEVfVklFV19IVFRQX0ZFRURTIiwiUk9MRV9UUklHR0VSX0lOR0VTVFMiLCJST0xFX1ZJRVdfTElOS1MiLCJST0xFX0NSRUFURV9" +
            "IVFRQX0ZFRURTIiwiUk9MRV9WSUVXX0lOR0VTVF9WSURFT19TVEFUVVNFUyIsIlJPTEVfVklFV19KT0JTIiwiUk9MRV9WSUVXX0lO" +
            "R0VTVF9WSURFT1MiLCJST0xFX0NSRUFURV9KT0JTIl19LCJ2aWRlby1zZXJ2aWNlIjp7InJvbGVzIjpbIlJPTEVfVVBEQVRFX1NVQ" +
            "kpFQ1RTIiwiUk9MRV9WSUVXX0NPTExFQ1RJT05TIiwiUk9MRV9WSUVXX0RJU1RSSUJVVElPTl9NRVRIT0RTIiwiUk9MRV9WSUVXX1" +
            "ZJREVPX1RZUEVTIiwiUk9MRV9VUERBVEVfQ0hBTk5FTFMiLCJST0xFX1RBR19WSURFT1MiLCJST0xFX1ZJRVdfQU5ZX1ZJREVPIiw" +
            "iUk9MRV9WSUVXX0FHRV9SQU5HRVMiLCJST0xFX1ZJRVdfRElTQ0lQTElORVMiLCJST0xFX1ZJRVdfQVRUQUNITUVOVF9UWVBFUyIs" +
            "IlJPTEVfVVBEQVRFX1ZJREVPUyIsIlJPTEVfVVBEQVRFX0NPTExFQ1RJT05TIiwiUk9MRV9WSUVXX0NPTlRSQUNUUyIsIlJPTEVfR" +
            "EVMRVRFX1RBR1MiLCJST0xFX1ZJRVdfVEFHUyIsIlJPTEVfVklFV19ESVNBQkxFRF9WSURFT1MiLCJST0xFX0lOU0VSVF9DSEFOTk" +
            "VMUyIsIlJPTEVfSU5TRVJUX1RBR1MiLCJST0xFX1ZJRVdfTEVHQUxfUkVTVFJJQ1RJT05TIiwiUk9MRV9VUERBVEVfRElTQ0lQTEl" +
            "ORVMiLCJST0xFX0lOU0VSVF9DT0xMRUNUSU9OUyIsIlJPTEVfVklFV19BTllfQ09MTEVDVElPTiIsIlJPTEVfQ1JFQVRFX1NVQkpFQ" +
            "1RTIiwiUk9MRV9WSUVXX0NPTlRFTlRfV0FSTklOR1MiLCJST0xFX1JBVEVfVklERU9TIiwiUk9MRV9ET1dOTE9BRF9WSURFTyIsIlJ" +
            "PTEVfREVMRVRFX1NVQkpFQ1RTIiwiUk9MRV9WSUVXX0NPTlRFTlRfUEFSVE5FUlMiLCJST0xFX0RPV05MT0FEX1RSQU5TQ1JJUFQi" +
            "LCJST0xFX0RFTEVURV9DT0xMRUNUSU9OUyIsIlJPTEVfVklFV19NQVJLRVRJTkdfU1RBVFVTRVMiLCJST0xFX0lOU0VSVF9ESVNDS" +
            "VBMSU5FUyIsIlJPTEVfU0hBUkVfVklERU9TIiwiUk9MRV9JTlNFUlRfRVZFTlRTIiwiUk9MRV9WSUVXX0NIQU5ORUxTIiwiUk9MRV" +
            "9WSUVXX0NPTlRFTlRfQ0FURUdPUklFUyIsIlJPTEVfVklFV19WSURFT1MiXX0sIm9yZGVyLXNlcnZpY2UiOnsicm9sZXMiOlsiUk9" +
            "MRV9VUERBVEVfT1JERVJTIiwiUk9MRV9WSUVXX09SREVSUyIsIlJPTEVfQ1JFQVRFX09SREVSUyJdfSwidXNlci1zZXJ2aWNlIjp7" +
            "InJvbGVzIjpbIlJPTEVfVklFV19DT05URU5UX1BBQ0tBR0VTIiwiUk9MRV9TWU5DSFJPTklaRV9VU0VSU19IVUJTUE9UIiwiUk9M" +
            "RV9WSUVXX0FDQ0VTU19SVUxFUyIsIlJPTEVfVVBEQVRFX0FDQ0VTU19SVUxFUyIsIlJPTEVfU1lOQ0hST05JWkVfVVNFUlNfS0VZQ" +
            "0xPQUsiLCJST0xFX0lOU0VSVF9PUkdBTklTQVRJT05TIiwiUk9MRV9VUERBVEVfT1JHQU5JU0FUSU9OUyIsIlJPTEVfVklFV19PUk" +
            "dBTklTQVRJT05TIiwiUk9MRV9VUERBVEVfQ09OVEVOVF9QQUNLQUdFUyJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJ2aWV3LXByb2Z" +
            "pbGUiXX19LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJNYXJjaW4gS" +
            "mFuaWsiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtYXJjaW5AYm9jbGlwcy5jb20iLCJnaXZlbl9uYW1lIjoiTWFyY2luIiwiZmFtaW" +
            "x5X25hbWUiOiJKYW5payIsImVtYWlsIjoibWFyY2luQGJvY2xpcHMuY29tIn0.QkYV9y6PXXWZgbvVOg8hGHFsm0uWza74DWioW6a" +
            "SLP1AUDBKgGCvK3BExhE-U9sA_o9eiXXKouDbDgWlJqmqPSGBrsr7Une5dDSAM8-a7rsMAYrM843frBen4QvGVhF2zVXAkiMrG25" +
            "5D2S79IW8lEXeTg9ojb6f962pItDsM1e2xjDWXv88GhUosD9YHokTjH6l8umR1ouq45-ncMhNmGwwl-cdIyC9n0u_zdfduTH7jIaF" +
            "wEkLfscNkY8vxDzV5FJxBijS0PzTvqHyHoFVcu2J7NNWbzPX-WCZvKuQ6AyJm_sPRDZk0bshDwSWY3f3qVWsMTtkR0c1UeJl4eDTtQ"

        @Test
        fun `token requests are proxied to keycloak`() {
            keycloakMock.register(
                get(urlEqualTo("/auth/realms/boclips/protocol/openid-connect/token"))
                    .willReturn(
                        aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody("""{"access_token":"$token"}""")
                    )
            )

            val response = restTemplate.getForObject("/v1/token", TokenResponse::class.java)
            assertThat(response!!.access_token).isEqualTo(token)
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
    fun `gateway request propagates X-Forwarded-* headers when present`() {
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

        val headers = HttpHeaders().apply { this.origin = "http://localhost:aaa.bbb" }
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
        headers.add("Origin", "http://localhost:aaa.bbb")
        val response = restTemplate.exchange<String>(
            "/v1/http-feeds/foo",
            HttpMethod.GET,
            HttpEntity(null, headers),
            String::class.java
        )

        val allowedOrigins = response.headers["Access-Control-Allow-Origin"]

        assertThat(allowedOrigins).contains("http://localhost:aaa.bbb")
        assertThat(allowedOrigins).doesNotContain("*")
    }
}
