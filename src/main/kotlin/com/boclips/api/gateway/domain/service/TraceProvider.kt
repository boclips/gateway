package com.boclips.api.gateway.domain.service

import com.boclips.api.gateway.domain.model.TraceId


interface TraceProvider {
    fun getTraceId(): TraceId?
}
