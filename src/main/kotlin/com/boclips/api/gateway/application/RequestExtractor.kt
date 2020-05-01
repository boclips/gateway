package com.boclips.api.gateway.application

import com.boclips.api.gateway.config.proxying.RoutesConfig.Companion.RETRIEVE_TOKEN_PATH
import com.boclips.api.gateway.domain.model.RequestDomain
import mu.KLogging
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component

@Component
class RequestExtractor {
    companion object : KLogging()

    fun extract(request: ServerHttpRequest): RequestDomain {
        val requestUri = request.uri

        return RequestDomain(
                protocol = request.headers["x-forwarded-proto"]?.firstOrNull() ?: requestUri.scheme,
                host = buildHost(request),
                port = request.headers["x-forwarded-port"]?.first()?.toInt() ?: 443,
                headers = request.headers["authorization"]?.firstOrNull()?.let { mapOf("Authorization" to it) }
                        ?: emptyMap()
        )
    }

    private fun buildHost(request: ServerHttpRequest): String {
        val forwardedHostHeader = request.headers["x-forwarded-host"]?.firstOrNull()
        val host = when {
            request.uri.path == RETRIEVE_TOKEN_PATH -> request.uri.host.replace("api.", "login.")
            forwardedHostHeader != null -> forwardedHostHeader
            else -> request.uri.host
        }
        logger.debug { "Forwarded host for request ${request.uri.toURL()} -> $host" }
        return host
    }

}

