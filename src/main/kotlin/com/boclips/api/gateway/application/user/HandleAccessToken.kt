package com.boclips.api.gateway.application.user

import com.boclips.api.gateway.application.JwtDecoder
import mu.KLogging
import org.springframework.stereotype.Component

@Component
class HandleAccessToken(
    val createApiUser: CreateApiUser
) {
    companion object : KLogging()

    operator fun invoke(accessToken: String) {
        val decodedJWT = JwtDecoder.safeDecode(accessToken)!!

        val customBoclipsUserId = decodedJWT.getClaim("boclips_user_id").asString()

        try {
            val subject = decodedJWT.subject ?: throw IllegalStateException()

            customBoclipsUserId?.let {
                createApiUser(
                    serviceAccountUserId = subject,
                    boclipsUserId = it
                )
            }
        } catch (e: Exception) {
            logger.error(e) { "Cannot create user from custom claim with id: $customBoclipsUserId" }
        }
    }
}