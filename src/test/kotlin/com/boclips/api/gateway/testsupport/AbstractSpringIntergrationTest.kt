package com.boclips.api.gateway.testsupport

import com.boclips.api.gateway.testsupport.AbstractSpringIntergrationTest.Companion.MARKETING_SERVICE_PORT
import com.boclips.api.gateway.config.RoutingProperties
import com.boclips.api.gateway.testsupport.AbstractSpringIntergrationTest.Companion.VIDEO_INGESTOR_PORT
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = [
    "gateway.services.marketingServiceUrl=http://localhost:$MARKETING_SERVICE_PORT",
    "gateway.services.videoIngestorUrl=http://localhost:$VIDEO_INGESTOR_PORT"
])
abstract class AbstractSpringIntergrationTest {

    @LocalServerPort
    lateinit var appPort: String

    companion object {
        const val MARKETING_SERVICE_PORT = 8090
        const val VIDEO_INGESTOR_PORT = 8091

        val marketingServiceWireMockServer = WireMockServer(options().port(MARKETING_SERVICE_PORT))
        val videoIngestorWireMockServer = WireMockServer(options().port(VIDEO_INGESTOR_PORT))
        val marketingServiceMock = WireMock("localhost", MARKETING_SERVICE_PORT)
        val videoIngestorMock = WireMock("localhost", VIDEO_INGESTOR_PORT)


        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            marketingServiceWireMockServer.start()
            videoIngestorWireMockServer.start()
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            marketingServiceWireMockServer.stop()
            videoIngestorWireMockServer.stop()
        }

    }

    @Autowired
    private lateinit var restTemplateBuilder: RestTemplateBuilder

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var routingProperties: RoutingProperties

    lateinit var restTemplate: RestTemplate
    lateinit var gatewayBaseUrl: String


    @BeforeEach
    fun setUp() {
        gatewayBaseUrl = "http://localhost:$appPort"
        restTemplate = restTemplateBuilder.rootUri(gatewayBaseUrl).build()
    }

}