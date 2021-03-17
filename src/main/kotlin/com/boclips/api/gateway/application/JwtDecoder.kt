package com.boclips.api.gateway.application

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import mu.KLogging

class JwtDecoder {
    companion object : KLogging() {
        fun safeDecode(token: String): DecodedJWT? = try {
            JWT.decode(token)
        } catch (e: Exception) {
            logger.warn { "Token decoding failed with a cause: ${e.cause}" }
            null
        }
    }
}
