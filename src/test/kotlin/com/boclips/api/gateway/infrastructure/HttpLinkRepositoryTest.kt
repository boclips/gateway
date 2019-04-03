package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.domain.model.RequestDomain
import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


class HttpLinkRepositoryTest : AbstractSpringIntegrationTest() {

    @Autowired
    lateinit var marketingServiceLinkRepository: HttpLinkRepository

    @Test
    fun `extracts all available links`() {
        marketingServiceMock.register(get(urlEqualTo("/v1/"))
                .willReturn(aResponse()
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

        assertThat(marketingServiceLinkRepository.findAll(RequestDomain(protocol = "http", host = "example.com", port = 80)).map { it.href }.toIterable()).containsExactlyInAnyOrder(
                "http://example.com/1",
                "http://example.com/2"
        )
    }

}