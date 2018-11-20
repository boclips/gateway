package com.boclips.api.gateway.domain.model

data class RequestDomain(
        val protocol: String,
        val host: String,
        val port: Int
)