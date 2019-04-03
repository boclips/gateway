package com.boclips.api.gateway.domain.model

import reactor.core.publisher.Flux

interface LinkRepository {
    fun findAll(requestDomain: RequestDomain): Flux<Link>
}