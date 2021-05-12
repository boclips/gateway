package com.boclips.api.gateway.config

import io.opentracing.Tracer
import io.opentracing.noop.NoopTracerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConditionalOnProperty(value = ["opentracing.jaeger.enabled"], havingValue = "false", matchIfMissing = false)
@Configuration
class DisableJaegerWhenRequested {
    @Bean
    fun jaegerTracer(): Tracer {
        return NoopTracerFactory.create()
    }
}
