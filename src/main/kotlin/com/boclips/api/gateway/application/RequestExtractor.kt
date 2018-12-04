package com.boclips.api.gateway.application

import com.boclips.api.gateway.domain.model.RequestDomain
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component

@Component
class RequestExtractor {
    fun extract(request: ServerHttpRequest): RequestDomain {
        val requestUri = request.uri
        return RequestDomain(
                protocol = request.headers["x-forwarded-proto"]?.firstOrNull() ?: requestUri.scheme,
                host = request.headers["x-forwarded-host"]?.firstOrNull() ?: requestUri.host,
                port = request.headers["x-forwarded-port"]?.firstOrNull()?.toInt() ?: requestUri.port
        )
    }
}

