package com.boclips.api.gateway

import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.http.*
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.web.client.HttpClientErrorException

class CorsConfigIntegrationTest : AbstractSpringIntegrationTest() {

    private val testPath = "/v1/http-feeds/foo"

    @ParameterizedTest
    @ValueSource(strings = [
        "http://localhost:aaa.bbb",
        "http://localhost:ccc.ddd"
    ])
    fun `allows requests with known origin`(host: String) {
        videoIngestorMock.register(get(urlEqualTo(testPath))
            .willReturn(aResponse().withBody("hello"))
        )

        val response = fireRequest(host)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `does not allow requests with unknown origins`() {
        videoIngestorMock.register(get(urlEqualTo(testPath))
            .willReturn(aResponse().withBody("hello"))
        )

        val ex = assertThrows<HttpClientErrorException> { fireRequest("www.example.com") }

        assertThat(ex).hasMessageContaining("403 Forbidden")
    }

    @Test
    fun `sets preflight Vary headers for OPTIONS responses`() {
        videoServiceMock.register(options(urlEqualTo("/v1/subjects"))
            .willReturn(aResponse().withBody("this-would-be-blank"))
        )
        fireRequest("/v1/subjects", OPTIONS)
            .let { response -> response.headers["Vary"] }!!
            .let { vary ->
                assertThat(vary).contains(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD)
                assertThat(vary).contains(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS)
            }
    }

    @Test
    fun `doesn't set preflight Vary headers for GET responses (enable Cloud CDN caching)`() {
        videoServiceMock.register(get(urlEqualTo("/v1/disciplines"))
            .willReturn(aResponse().withBody("some-json"))
        )
        fireRequest("/v1/disciplines", GET).run { headers["Vary"] }!!.let { vary ->
            assertThat(vary).doesNotContain(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD)
            assertThat(vary).doesNotContain(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS)
            assertThat(vary).contains(HttpHeaders.ORIGIN)
            assertThat(vary).contains(HttpHeaders.ACCEPT_ENCODING)
        }
    }

    private fun fireRequest(path: String, method: HttpMethod): ResponseEntity<String> =
        fireRequest(path, method, HttpHeaders())

    private fun fireRequest(host: String): ResponseEntity<String> =
        fireRequest(testPath, GET, HttpHeaders().apply { add("Origin", host) })

    private fun fireRequest(path: String, method: HttpMethod, headers: HttpHeaders): ResponseEntity<String> =
        restTemplate.exchange<String>(
            path,
            method,
            HttpEntity(null, headers),
            String::class.java
        )
}
