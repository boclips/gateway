package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.github.tomakehurst.wiremock.client.WireMock
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


internal class HttpLinkRepositoryTest : AbstractSpringIntegrationTest() {

    @Autowired
    lateinit var marketingServiceLinkRepository: HttpLinkRepository

    @Test
    fun `extracts all available links`() {
        marketingServiceMock.register(WireMock.get(WireMock.urlEqualTo("/v1/"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/hal+json")
                        .withBody(
                                """
                                    {
                                      "_links": {
                                            "peanuts": {
                                                "href": "http://example.com/1",
                                                "templated": true
                                            },
                                            "walnuts": {
                                                "href": "http://example.com/2"
                                            }
                                        }
                                    }
                                """
                        )))

        Assertions.assertThat(marketingServiceLinkRepository.findAll().map { it.href }).containsExactlyInAnyOrder(
                "http://example.com/1",
                "http://example.com/2"
        )
    }
}