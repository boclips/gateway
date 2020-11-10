package com.boclips.api.gateway.application

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT

class JwtDecoder {
    companion object {
        fun safeDecode(token: String): DecodedJWT? = try {
            JWT.decode(token)
        } catch (e: Exception) {
            null
        }
    }
}
