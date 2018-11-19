package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.testsupport.AbstractSpringIntergrationTest
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class VideoIngestorLinkRepositoryTest : AbstractSpringIntergrationTest() {

    @Autowired
    lateinit var subject: VideoIngestorLinkRepository

    @Test
    fun `extracts all available links`() {
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

        assertThat(subject.findAll().map { it.href }).containsExactlyInAnyOrder(
                "${routingProperties.videoIngestorUrl}/v1/jobs?page=0&size=20",
                "${routingProperties.videoIngestorUrl}/v1/jobs/{id}"
        )
    }
}