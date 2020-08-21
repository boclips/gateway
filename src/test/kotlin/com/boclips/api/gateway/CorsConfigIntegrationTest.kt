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
        "https://app.nuadu.com",
        "https://app.nuadu.pl",

        "https://local.lessonplanet.com",
        "https://local.lessonplanet.com:3000",
        "https://staging.lessonplanet.com",
        "https://feature.lessonplanet.com",
        "https://lessonplanet.com",
        "https://www.lessonplanet.com",

        "https://read.kortext.com",
        "https://read.dev.kortext.com",
        "https://read.qa.kortext.com",
        "https://read.uat.kortext.com",
        "https://read-api.kortext.com",
        "https://read-api.dev.kortext.com",
        "https://read-api.qa.kortext.com",
        "https://read-api.uat.kortext.com",

        "http://generalonologicsoft.com",

        "https://testnew.empass.mobi",
        "https://app.learntinue.ai"
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
