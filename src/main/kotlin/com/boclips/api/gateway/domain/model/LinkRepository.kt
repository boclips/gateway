package com.boclips.api.gateway.domain.model

interface LinkRepository {
    fun findAll(requestDomain: RequestDomain): List<Link>
}