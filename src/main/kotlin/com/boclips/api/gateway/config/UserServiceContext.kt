package com.boclips.api.gateway.config

import com.boclips.api.gateway.config.properties.UserClientProperties
import com.boclips.users.api.httpclient.UsersClient
import com.boclips.users.api.httpclient.helper.ServiceAccountCredentials
import com.boclips.users.api.httpclient.helper.ServiceAccountTokenFactory
import feign.okhttp.OkHttpClient
import feign.opentracing.TracingClient
import io.opentracing.Tracer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("!fake-user-service")
@Configuration
class UserServiceContext {
    @Bean
    fun usersClient(userClientProperties: UserClientProperties, tracer: Tracer) =
        UsersClient.create(
            apiUrl = userClientProperties.baseUrl,
            tokenFactory = userClientProperties.tokenFactory(),
            feignClient = createTracingClient(tracer),
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

fun createTracingClient(tracer: Tracer): TracingClient {
    val delegate = OkHttpClient()
    return TracingClient(delegate, tracer)
}
