package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.testsupport.AbstractSpringIntergrationTest
import com.github.tomakehurst.wiremock.client.WireMock
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


internal class MarketingServiceLinkRepositoryTest : AbstractSpringIntergrationTest() {

    @Autowired
    lateinit var subject: MarketingServiceLinkRepository

    @Test
    fun `extracts all available links`() {
        marketingServiceMock.register(WireMock.get(WireMock.urlEqualTo("/v1/"))
                .willReturn(WireMock.aResponse()
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

        Assertions.assertThat(subject.findAll().map { it.href }).containsExactlyInAnyOrder(
                "${routingProperties.marketingServiceUrl}/v1/marketing-collections/{id}",
                "${routingProperties.marketingServiceUrl}/v1/marketing-collections"
        )
    }
}