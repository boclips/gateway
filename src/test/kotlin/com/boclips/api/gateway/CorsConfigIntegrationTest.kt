package com.boclips.api.gateway

import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.github.tomakehurst.wiremock.client.WireMock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.http.*
import org.springframework.web.client.HttpClientErrorException

class CorsConfigIntegrationTest : AbstractSpringIntegrationTest() {

    private val testPath = "/v1/http-feeds/foo"

    @ParameterizedTest
    @ValueSource(strings = [
        "http://localhost:8080",
        "http://localhost:8081",
        "http://localhost:3000",

        "https://teachers.testing-boclips.com",
        "https://teachers.staging-boclips.com",
        "https://teachers.boclips.com",

        "https://backoffice.testing-boclips.com",
        "https://backoffice.staging-boclips.com",
        "https://backoffice.boclips.com",

        "https://publishers.testing-boclips.com",
        "https://publishers.staging-boclips.com",
        "https://publishers.boclips.com",

        "https://api.testing-boclips.com",
        "https://api.staging-boclips.com",
        "https://api.boclips.com",

        "https://lti.testing-boclips.com",
        "https://lti.staging-boclips.com",
        "https://lti.boclips.com",

        "https://login.testing-boclips.com",
        "https://login.staging-boclips.com",
        "https://login.boclips.com",

        "https://testing-boclips.com",
        "https://staging-boclips.com",
        "https://boclips.com",

        "https://www.testing-boclips.com",
        "https://www.staging-boclips.com",
        "https://www.boclips.com",

        "https://myviewboard.com",
        "https://myviewclip.myviewboard.cloud",
        "https://stage.myviewboard.com",
        "https://myviewclip.stage.myviewboard.cloud",
        "https://myviewboardclips.com",

        "http://nuadu-system-www-2.0:8000",
        "https://test-k8s-system.nuadu.com",
        "https://test2-k8s-system.nuadu.com",
        "https://test3-k8s-system.nuadu.com",
        "https://app.nuadu.com"
    ])
    fun `allows requests with known origin`(host: String) {
        videoIngestorMock.register(WireMock.get(WireMock.urlEqualTo(testPath))
                .willReturn(WireMock.aResponse().withBody("hello"))
        )

        val response = fireRequestWithOrigin(host)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `does not allow requests with unknown origins`() {
        videoIngestorMock.register(WireMock.get(WireMock.urlEqualTo(testPath))
                .willReturn(WireMock.aResponse().withBody("hello"))
        )

        try {
            fireRequestWithOrigin("www.example.com")
        } catch (ex: HttpClientErrorException) {
            assertThat(ex).hasMessageContaining("403 Forbidden")
        }
    }

    private fun fireRequestWithOrigin(host: String): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.add("Origin", host)
        return restTemplate.exchange<String>(testPath, HttpMethod.GET, HttpEntity(null, headers), String::class.java)
    }

}
