package com.boclips.api.gateway.presentation

import com.boclips.api.gateway.application.LinkActions
import org.springframework.http.server.reactive.ServerHttpRequest
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
    fun getBackOfficeLinks(serverHttpRequest: ServerHttpRequest): Mono<LinksResource> {
        return Mono.just(linkActions.getAllLinks(serverHttpRequest))
    }

    @GetMapping("", "/")
    fun getCustomerFacingLinks(serverHttpRequest: ServerHttpRequest): Mono<LinksResource> {
        return Mono.just(linkActions.getCustomerFacingLinks(serverHttpRequest))
    }

}