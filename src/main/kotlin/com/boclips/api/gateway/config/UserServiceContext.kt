package com.boclips.api.gateway.config

import com.boclips.api.gateway.config.properties.UserClientProperties
import com.boclips.users.api.httpclient.ApiUsersClient
import com.boclips.users.api.httpclient.UsersClient
import com.boclips.users.api.httpclient.helper.ServiceAccountCredentials
import com.boclips.users.api.httpclient.helper.ServiceAccountTokenFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("!fake-user-service")
@Configuration
class UserServiceContext {
    @Bean
    fun usersClient(userClientProperties: UserClientProperties) =
        UsersClient.create(
            apiUrl = userClientProperties.baseUrl,
            tokenFactory = userClientProperties.tokenFactory()
        )

    @Bean
    fun apiUsersClient(userClientProperties: UserClientProperties) =
        ApiUsersClient.create(
            apiUrl = userClientProperties.baseUrl,
            tokenFactory = userClientProperties.tokenFactory()
        )
}

fun UserClientProperties.tokenFactory() =
    ServiceAccountTokenFactory(
        serviceAccountCredentials = ServiceAccountCredentials(
            authEndpoint = tokenUrl,
            clientId = clientId,
            clientSecret = clientSecret
        )
    )
