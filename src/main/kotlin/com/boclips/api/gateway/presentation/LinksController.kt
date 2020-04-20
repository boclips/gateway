package com.boclips.api.gateway.presentation

import com.boclips.api.gateway.application.LinkActions
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1")
class LinksController(
        val linkActions: LinkActions
) {

    @GetMapping("/admin", "/admin/")
    fun getBackOfficeLinks(serverHttpRequest: ServerHttpRequest, response: ServerHttpResponse): Mono<LinksResource> {
        response.headers.set("Content-Type", "application/hal+json")
        return linkActions.getAllLinks(serverHttpRequest)
    }

    @GetMapping("", "/")
    fun getCustomerFacingLinks(serverHttpRequest: ServerHttpRequest, response: ServerHttpResponse): Mono<LinksResource> {
        response.headers.set("Content-Type", "application/hal+json")
        return linkActions.getCustomerFacingLinks(serverHttpRequest)
    }

}
