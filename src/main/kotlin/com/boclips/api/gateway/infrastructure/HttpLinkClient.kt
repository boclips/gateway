package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.domain.model.RequestDomain
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import java.net.URI

@Component
class HttpLinkClient(
        private val restTemplateBuilder: RestTemplateBuilder
) {
    fun fetch(uri: URI, requestDomain: RequestDomain): Links {
        val headers = HttpHeaders().apply {
            set("X-Forwarded-Host", requestDomain.host)
            set("X-Forwarded-Port", requestDomain.port.toString())
            set("X-Forwarded-Proto", requestDomain.protocol)
        }
        val entity = HttpEntity(null, headers)
        val restTemplate = restTemplateBuilder.rootUri(uri.toString()).build()
        return restTemplate.exchange("/v1/", HttpMethod.GET, entity, Links::class.java).body
                ?: Links(_links = emptyMap())
    }
}