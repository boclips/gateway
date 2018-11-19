package com.boclips.api.gateway.presentation

import com.boclips.api.gateway.application.GetLinks
import com.boclips.api.gateway.domain.model.LinkRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/v1")
class LinksController(
        val getLinks: GetLinks
) {

    @GetMapping("", "/")
    fun getLinks(uriBuilder: UriComponentsBuilder) = getLinks.execute(uriBuilder.toUriString())

}