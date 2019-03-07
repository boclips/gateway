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

    @ParameterizedTest
    @ValueSource(strings = [
        "http://localhost:8080",
        "http://localhost:8081",
        "http://localhost:3000",
        "https://teachers.testing-boclips.com",
        "https://teachers.staging-boclips.com",
        "https://teachers.boclips.com",
        "https://boclips.com",
        "https://publishers.boclips.com",
        "https://login.testing-boclips.com",
        "https://login.staging-boclips.com",
        "https://login.boclips.com"
    ])
    fun `allows requests with known origin`(host: String) {
        marketingServiceMock.register(WireMock.get(WireMock.urlEqualTo("/v1/marketing-collections"))
                .willReturn(WireMock.aResponse().withBody("hello"))
        )

        val response = fireRequestWithOrigin(host)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `does not allow requests with unknown origins`() {
        marketingServiceMock.register(WireMock.get(WireMock.urlEqualTo("/v1/marketing-collections"))
                .willReturn(WireMock.aResponse().withBody("hello"))
        )

        try {
            fireRequestWithOrigin("www.example.com")
        } catch (ex: HttpClientErrorException) {
            assertThat(ex).hasMessage("403 Forbidden")
        }
    }

    private fun fireRequestWithOrigin(host: String): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.add("Origin", host)
        return restTemplate.exchange<String>("/v1/marketing-collections", HttpMethod.GET, HttpEntity(null, headers), String::class.java)
    }

}