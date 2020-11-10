package com.boclips.api.gateway.application.user

import com.boclips.api.gateway.application.JwtDecoder
import org.springframework.stereotype.Component

@Component
class CreateApiUserFromToken(
    val createApiUser: CreateApiUser
) {

    operator fun invoke(accessToken: String) {
        val decodedJWT = JwtDecoder.safeDecode(accessToken)!!

        createApiUser(
            serviceAccountUserId = decodedJWT.getClaim("sub")!!.asString(),
            boclipsUserId = decodedJWT.getClaim("boclips_user_id")?.asString()
        )
    }
}
