package com.boclips.api.gateway.domain.model

import org.springframework.web.util.UriComponentsBuilder

data class Link (
        val href: String,
        val templated: Boolean = false,
        val rel: String
) {
    fun replaceDomain(newDomain: String): Link {
        val newDomainBuilder = UriComponentsBuilder.fromUriString(newDomain).build()
        val hrefUriBuilder = UriComponentsBuilder.fromUriString(href)
                .scheme(newDomainBuilder.scheme)
                .host(newDomainBuilder.host)
                .port(newDomainBuilder.port)


        return copy(href = hrefUriBuilder.toUriString())
    }
}