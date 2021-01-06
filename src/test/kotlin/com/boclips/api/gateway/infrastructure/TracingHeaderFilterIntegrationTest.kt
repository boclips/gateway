package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.domain.model.TraceId
import com.boclips.api.gateway.domain.service.TraceProvider
import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.github.tomakehurst.wiremock.client.WireMock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType

class TracingHeaderFilterIntegrationTest : AbstractSpringIntegrationTest() {
    @MockBean
    lateinit var mockTraceProvider: TraceProvider

    @Test
    fun `returns trace id in response header`() {
        // Any wiremock will do here
        videoIngestorMock.register(
            WireMock.head(WireMock.urlEqualTo("/v1/jobs"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "text/plain")
                )
        )

        whenever(mockTraceProvider.getTraceId())
            .thenReturn(TraceId(value = "a-trace-id"))

        val headers = restTemplate.headForHeaders("/v1/jobs")

        val traceId = headers["X-Boclips-Trace-Id"]?.first()
        val contentType = headers.contentType

        assertThat(traceId).isEqualTo("a-trace-id")
        assertThat(contentType).isEqualTo(MediaType.TEXT_PLAIN)
    }


    @Test
    fun `does not fall over when no trace present`() {
        // Any wiremock will do here
        videoIngestorMock.register(
            WireMock.head(WireMock.urlEqualTo("/v1/jobs"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "text/plain")
                )
        )

        whenever(mockTraceProvider.getTraceId())
            .thenReturn(null)

        val headers = restTemplate.headForHeaders("/v1/jobs")

        val traceId = headers["X-Boclips-Trace-Id"]?.first()

        assertThat(traceId).isNull()
    }
}
