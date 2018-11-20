package com.boclips.api.gateway.domain.model

data class Link(
        val href: String,
        val templated: Boolean = false,
        val rel: String
)