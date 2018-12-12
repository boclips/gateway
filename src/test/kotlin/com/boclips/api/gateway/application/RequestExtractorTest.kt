package com.boclips.api.gateway.application

import com.boclips.api.gateway.config.proxying.RoutesConfig.Companion.RETRIEVE_TOKEN_PATH
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.mock.http.server.reactive.MockServerHttpRequest

class RequestExtractorTest {
    private val requestExtractor = RequestExtractor()

    @Test
    fun `extracts host port and protocol`() {
        val (protocol, host, port) = requestExtractor.extract(MockServerHttpRequest
                .get("http://localhost:8080")
                .header("X-Forwarded-host", "example.com")
                .header("X-Forwarded-port", "80")
                .header("X-Forwarded-proto", "https")
                .build())

        assertThat(host).isEqualTo("example.com")
        assertThat(port).isEqualTo(80)
        assertThat(protocol).isEqualTo("https")
    }

    @Test
    fun `extracts host port and protocol from uri when headers are not present`() {
        val (protocol, host, port) = requestExtractor.extract(MockServerHttpRequest
                .get("http://localhost:8080")
                .build())

        assertThat(host).isEqualTo("localhost")
        assertThat(port).isEqualTo(8080)
        assertThat(protocol).isEqualTo("http")
    }

    @Test
    fun `sets token retrieve original header - because keycloak sets iss and it needs to match with the URL configured in apps`() {
        val host = requestExtractor.extract(MockServerHttpRequest
                .get("http://api.boclips.com$RETRIEVE_TOKEN_PATH")
                .build()).host

        assertThat(host).isEqualTo("login.boclips.com")
    }

    @Test
    fun `extracts Authorization header if present`() {
        val (protocol, host, port, headers) = requestExtractor.extract(MockServerHttpRequest
                .get("http://localhost:8080")
                .header("Authorization", "poke me in the coconut")
                .build())

        assertThat(host).isEqualTo("localhost")
        assertThat(port).isEqualTo(8080)
        assertThat(protocol).isEqualTo("http")
        assertThat(headers).containsEntry("Authorization", "poke me in the coconut")
    }
}