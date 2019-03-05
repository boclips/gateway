package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.domain.model.RequestDomain
import mu.KLogging
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
    companion object : KLogging()

    fun fetch(uri: URI, requestDomain: RequestDomain): Links {
        val headers = HttpHeaders().apply {
            requestDomain.headers.entries.forEach { set(it.key, it.value) }

            set("X-Forwarded-Host", requestDomain.host)
            set("X-Forwarded-Port", requestDomain.port.toString())
            set("X-Forwarded-Proto", requestDomain.protocol)
        }
        val entity = HttpEntity(null, headers)
        val restTemplate = restTemplateBuilder.rootUri(uri.toString()).build()

        return try {
            restTemplate.exchange("/v1/", HttpMethod.GET, entity, Links::class.java).body
                    ?: Links(_links = emptyMap())
        } catch (e: Exception) {
            logger.warn("Unable to fetch links from uri=$uri", e)
            Links(_links = emptyMap())
        }
    }
}