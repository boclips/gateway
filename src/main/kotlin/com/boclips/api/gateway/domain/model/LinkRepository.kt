package com.boclips.api.gateway.domain.model

interface LinkRepository {
    fun findAll(): List<Link>
}