package com.boclips.api.gateway.application

import com.boclips.api.gateway.config.proxying.RoutesConfig.Companion.RETRIEVE_TOKEN_PATH
import com.boclips.api.gateway.domain.model.RequestDomain
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import java.net.URI

@Component
class RequestExtractor {
    fun extract(request: ServerHttpRequest): RequestDomain {
        val requestUri = request.uri

        return RequestDomain(
                protocol = request.headers["x-forwarded-proto"]?.firstOrNull() ?: requestUri.scheme,
                host = buildHost(request),
                port = request.headers["x-forwarded-port"]?.firstOrNull()?.toInt() ?: requestUri.port,
                headers = request.headers["authorization"]?.firstOrNull()?.let { mapOf("Authorization" to it) }
                        ?: emptyMap()
        )
    }

    private fun buildHost(request: ServerHttpRequest) =
            request.headers["x-forwarded-host"]?.firstOrNull()
                    ?: if (request.uri.path == RETRIEVE_TOKEN_PATH)
                        request.uri.host.replace("api.", "login.")
                    else
                        request.uri.host

}

