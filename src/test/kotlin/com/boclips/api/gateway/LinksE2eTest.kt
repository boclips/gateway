package com.boclips.api.gateway

import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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
                                            },
                                            "marketingCollections": {
                                                "href": "${routingProperties.marketingServiceUrl}/v1/marketing-collections"
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
                    "jobDetails": {
                        "href": "$gatewayBaseUrl/v1/jobs/{id}",
                        "templated": true
                    },
                    "jobs": {
                        "href": "$gatewayBaseUrl/v1/jobs?page=0&size=20",
                        "templated": false
                    },
                    "marketingCollection": {
                        "href": "$gatewayBaseUrl/v1/marketing-collections/{id}",
                        "templated": true
                    },
                    "marketingCollections": {
                        "href": "$gatewayBaseUrl/v1/marketing-collections",
                        "templated": false
                    }
                }
            }
        """
        ))
    }
}