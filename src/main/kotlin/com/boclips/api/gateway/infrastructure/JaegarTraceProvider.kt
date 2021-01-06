package com.boclips.api.gateway.infrastructure

import com.boclips.api.gateway.domain.model.TraceId
import com.boclips.api.gateway.domain.service.TraceProvider
import io.opentracing.Tracer
import org.springframework.stereotype.Component

@Component
class JaegarTraceProvider(private val tracer: Tracer) : TraceProvider {
    override fun getTraceId(): TraceId? {
        return tracer.activeSpan()
            ?.context()
            ?.toSpanId()
            ?.let { id ->
                TraceId(value = id)
            }
    }
}
