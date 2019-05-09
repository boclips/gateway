package com.boclips.api.gateway

import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType

class LinksE2eTest : AbstractSpringIntegrationTest() {

    @Test
    fun `aggregates services links`() {

        videoIngestorMock.register(get(urlEqualTo("/v1/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withBody(
                                """
                                    {
                                        "_links": {
                                            "jobs": {
                                                "href": "${routingProperties.videoIngestorUrl}/v1/jobs?page=0&size=20"
                                            },
                                            "jobDetails": {
                                                "href": "${routingProperties.videoIngestorUrl}/v1/jobs/{id}",
                                                "templated": true
                                            }
                                        }
                                    }
                                """
                        )))

        marketingServiceMock.register(get(urlEqualTo("/v1/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withBody(
                                """
                                    {
                                      "_links": {
                                            "marketingCollection": {
                                                "href": "${routingProperties.marketingServiceUrl}/v1/marketing-collections/{id}",
                                                "templated": true
                                            }
                                        }
                                    }
                                """
                        )))

        val response = restTemplate.getForEntity("/v1/admin", Map::class.java)
        assertThat(response.headers["Content-Type"]).contains("application/hal+json")
        assertThat(response.body).isEqualTo(objectMapper.readValue(
                """
            {
                "_links": {
                    "jobDetails": {
                        "href": "${routingProperties.videoIngestorUrl}/v1/jobs/{id}",
                        "templated": true
                    },
                    "jobs": {
                        "href": "${routingProperties.videoIngestorUrl}/v1/jobs?page=0&size=20",
                        "templated": false
                    },
                    "marketingCollection": {
                        "href": "${routingProperties.marketingServiceUrl}/v1/marketing-collections/{id}",
                        "templated": true
                    }
                }
            }
        """
        ))
    }

    @Test
    fun `aggregates customer-facing links`() {
        userServiceMock.register(get(urlEqualTo("/v1/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withBody(
                                """
                                    {
                                        "_links": {
                                            "users": {
                                                "href": "${routingProperties.userServiceUrl}/v1/users/couldnt-care-less"
                                            }
                                        }
                                    }
                                """
                        )))

        videoServiceMock.register(get(urlEqualTo("/v1/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withBody(
                                """
                                    {
                                        "_links": {
                                            "videos": {
                                                "href": "${routingProperties.videoServiceUrl}/v1/videos/couldnt-care-less"
                                            }
                                        }
                                    }
                                """
                        )))


        val response = restTemplate.getForObject("/v1/", Map::class.java)
        assertThat(response).isEqualTo(objectMapper.readValue(
                """
            {
                "_links": {
                    "users": {
                        "href": "${routingProperties.userServiceUrl}/v1/users/couldnt-care-less",
                        "templated": false
                    },
                    "videos": {
                        "href": "${routingProperties.videoServiceUrl}/v1/videos/couldnt-care-less",
                        "templated": false
                    }
                }
            }
        """
        ))
    }

    @Test
    fun `propagates Authorization header`() {
        userServiceMock.register(get(urlEqualTo("/v1/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                ))

        val headers = HttpHeaders().apply {
            set("Authorization", "the-seaguls-poked-in-my-pants")
        }
        val entity = HttpEntity(null, headers)
        restTemplate.exchange("/v1/", HttpMethod.GET, entity, Map::class.java)

        userServiceWireMockServer.verify(getRequestedFor(urlEqualTo("/v1/"))
                .withHeader("Authorization", equalTo("the-seaguls-poked-in-my-pants")))
    }
}
