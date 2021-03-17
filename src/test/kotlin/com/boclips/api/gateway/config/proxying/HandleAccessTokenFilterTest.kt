package com.boclips.api.gateway.config.proxying

import com.boclips.api.gateway.application.user.HandleAccessTokenCustomBoclipsClaims
import com.boclips.api.gateway.config.TokenResponse
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import reactor.test.StepVerifier

class HandleAccessTokenFilterTest {

    private val handleAccessToken = Mockito.mock(HandleAccessTokenCustomBoclipsClaims::class.java)
    private val publisher = HandleAccessTokenFilter(handleAccessToken)

    @Test
    fun `should return intact response upon successful completion for request with no access token`() {
        val requestBody = EMPTY_REQUEST
        StepVerifier
            .create(publisher(requestBody))
            .expectNext(requestBody)
            .expectComplete()
            .verify()
    }

    @Test
    fun `should return intact response upon successful completion for request with valid access token`() {
        val requestBody = EMPTY_REQUEST.copy(access_token = "valid-access-token")

        StepVerifier
            .create(publisher(requestBody))
            .expectNext(requestBody)
            .expectComplete()
            .verify()

        verify(handleAccessToken, times(1)).invoke("valid-access-token")
    }

    @Test
    fun `should return an error when invalid access token is given`() {
        val requestBody = EMPTY_REQUEST.copy(access_token = "invalid-access-token")

        given(handleAccessToken.invoke("invalid-access-token")).willThrow(NullPointerException::class.java)

        StepVerifier
            .create(publisher(requestBody))
            .expectError(NullPointerException::class.java)
            .verify()
    }

    private companion object {

        val EMPTY_REQUEST = TokenResponse(access_token = null,
            expires_in = null,
            refresh_expires_in = null,
            refresh_token = null,
            token_type = null,
            `not-before-policy` = null,
            session_state = null,
            scope = null
        )
    }
}
