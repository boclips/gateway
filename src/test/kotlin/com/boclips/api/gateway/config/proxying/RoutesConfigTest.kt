package com.boclips.api.gateway.config.proxying

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.boclips.api.gateway.config.TokenResponse
import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.boclips.users.api.factories.OrganisationResourceFactory
import com.boclips.users.api.factories.UserResourceFactory
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.containing
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class RoutesConfigTest: AbstractSpringIntegrationTest() {
    @Test
    fun `creates a valid user based on a boclips custom headers for x-www-form-urlencoded content type`() {
        usersClientFake.add(
            UserResourceFactory.sample(
                id = "a-subject",
                organisation = OrganisationResourceFactory.sampleDetails(id = "org-id")
            )
        )

        keycloakMock.register(WireMock.post(WireMock.urlEqualTo(RETRIEVE_TOKEN_PATH))
            .withHeader("Content-Type", containing(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
            .withHeader("Boclips-User-Id", equalTo("hello"))
            .withRequestBody(equalTo("field1=value1&field2=value2"))
            .willReturn(WireMock.aResponse()
                .withHeader("Content-Type", "application/hal+json")
                .withBody(
                    """
                            {
                              "access_token": "${createJWT()}"
                            }
                        """
                )))

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.set("Boclips-User-Id", "hello")

        val entity = HttpEntity<String>("field1=value1&field2=value2", headers)
        val tokenResponse = restTemplate.postForEntity("http://localhost:$appPort/v1/token", entity, TokenResponse::class.java)

        Assertions.assertThat(usersClientFake.getUser("THE-boclips_user_id")).isNotNull
        Assertions.assertThat(tokenResponse.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(tokenResponse.body!!.access_token).isEqualTo(createJWT())
    }

    companion object {
        const val RETRIEVE_TOKEN_PATH = "/auth/realms/boclips/protocol/openid-connect/token"

        fun createJWT(): String {
            return JWT.create()
                .withClaim("boclips_user_id", "THE-boclips_user_id")
                .withClaim("external_user_id", "THE-external_user_id")
                .withClaim("sub", "a-subject")
                .sign(Algorithm.HMAC256("secret"))
        }
    }
}
