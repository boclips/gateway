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

    operator fun invoke(serviceAccountUserId: String, boclipsUserId: String?) {
        boclipsUserId?.let {
            val serviceAccountUser = usersClient.getUser(serviceAccountUserId)
            apiUsersClient.createApiUser(it, CreateApiUserRequest(serviceAccountUser.organisation?.id!!))
        }
    }
}
