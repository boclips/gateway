package com.boclips.api.gateway.application.user

import com.boclips.users.api.httpclient.UsersClient
import com.boclips.users.api.request.user.CreateUserRequest
import feign.FeignException
import org.springframework.stereotype.Component

@Component
class CreateApiUser(
    val usersClient: UsersClient
) {
    operator fun invoke(serviceAccountUserId: String, boclipsUserId: String, externalUserId: String) {
        if (userAlreadyExists(boclipsUserId)) {
            return
        }

        val serviceAccountUser = usersClient.getUser(serviceAccountUserId)

        val organisationId = serviceAccountUser.organisation?.id
            ?: throw IllegalStateException(
                "Service account user: ${serviceAccountUser.id} does not have an organisation"
            )

        usersClient.createApiUser(
            CreateUserRequest.CreateApiUserRequest(
                apiUserId = boclipsUserId,
                organisationId = organisationId,
                externalUserId = externalUserId
            )
        )
    }

    private fun userAlreadyExists(boclipsUserId: String) = try {
        usersClient.headUser(boclipsUserId)
        true
    } catch (err: FeignException.NotFound) {
        false
    }
}
