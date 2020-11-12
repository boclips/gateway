package com.boclips.api.gateway.application.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.nhaarman.mockitokotlin2.*
import org.junit.Test
import org.junit.jupiter.api.assertDoesNotThrow

class HandleAccessTokenTest {
    @Test
    fun `tries to create a user when custom claim is present`() {
        val token = JWT.create()
            .withSubject("boclips-service-id")
            .withClaim("boclips_user_id", "boclips_user_id")
            .sign(Algorithm.none())

        val createApiUserMock = mock<CreateApiUser>()
        val handleAccessToken = HandleAccessToken(createApiUserMock)
        handleAccessToken(token)

        verify(createApiUserMock, times(1)).invoke("boclips-service-id", "boclips_user_id")
    }

    @Test
    fun `does not try to create a user when custom claim is not present`() {
        val token = JWT.create()
            .withSubject("boclips-service-id")
            .sign(Algorithm.none())

        val createApiUserMock = mock<CreateApiUser>()
        val handleAccessToken = HandleAccessToken(createApiUserMock)
        handleAccessToken(token)

        verify(createApiUserMock, never()).invoke(anyOrNull(), anyOrNull())
    }

    @Test
    fun `does not try to create a user if subject claim is not present`() {
        val token = JWT.create()
            .withClaim("boclips_user_id", "boclips_user_id")
            .sign(Algorithm.none())

        val createApiUserMock = mock<CreateApiUser>()
        val handleAccessToken = HandleAccessToken(createApiUserMock)
        handleAccessToken(token)

        verify(createApiUserMock, never()).invoke(anyOrNull(), anyOrNull())
    }

    @Test
    fun `handles exception thrown by createApiUser`() {
        val token = JWT.create()
            .withSubject("boclips-service-id")
            .withClaim("boclips_user_id", "boclips_user_id")
            .sign(Algorithm.none())

        val createApiUserMock = mock<CreateApiUser>()
        whenever(
            createApiUserMock
                .invoke("boclips-service-id", "boclips_user_id"))
            .doThrow(RuntimeException())

        val handleAccessToken = HandleAccessToken(createApiUserMock)

        assertDoesNotThrow { handleAccessToken(token) }
    }
}
