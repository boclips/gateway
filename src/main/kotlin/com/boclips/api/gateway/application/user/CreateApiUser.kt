package com.boclips.api.gateway.application.user

import com.boclips.users.api.httpclient.ApiUsersClient
import com.boclips.users.api.httpclient.UsersClient
import com.boclips.users.api.request.CreateApiUserRequest
import org.springframework.stereotype.Component

@Component
class CreateApiUser(
    val usersClient: UsersClient,
    val apiUsersClient: ApiUsersClient
) {
    operator fun invoke(serviceAccountUserId: String, boclipsUserId: String) {
        val serviceAccountUser = usersClient.getUser(serviceAccountUserId)

        val organisationId = serviceAccountUser.organisation?.id
            ?: throw IllegalStateException(
                "Service account user: ${serviceAccountUser.id} does not have an organisation"
            )

        apiUsersClient.createApiUser(boclipsUserId, CreateApiUserRequest(organisationId))
    }
}
