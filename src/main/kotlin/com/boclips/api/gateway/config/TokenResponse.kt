package com.boclips.api.gateway.config

data class TokenResponse(
    val access_token: String?,
    val refresh_expires_in: String?,
    val refresh_token: String?,
    val token_type: String?,
    val `not-before-policy`: String?,
    val session_state: String?,
    val scope: String?,
)
