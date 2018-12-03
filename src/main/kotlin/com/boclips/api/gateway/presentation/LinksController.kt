package com.boclips.api.gateway.presentation

import com.boclips.api.gateway.application.GetLinks
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class LinksController(
        val getLinks: GetLinks
) {

    @GetMapping("/admin", "/admin/")
    fun getBackOfficeLinks(serverHttpRequest: ServerHttpRequest): LinksResource {
        return getLinks.execute(serverHttpRequest)
    }

}