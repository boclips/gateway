package com.boclips.api.gateway.testsupport.fakes

import com.boclips.users.api.httpclient.UsersClient
import com.boclips.users.api.httpclient.test.fakes.UsersClientFake
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("fake-user-service")
@Configuration
class UserServiceClientFake {
    @Bean
    fun usersClient(): UsersClient = UsersClientFake()
}
