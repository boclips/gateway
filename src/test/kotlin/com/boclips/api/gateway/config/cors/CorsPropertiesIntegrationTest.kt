package com.boclips.api.gateway.config.cors

import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class CorsPropertiesIntegrationTest: AbstractSpringIntegrationTest() {

    @Autowired
    lateinit var corsProperties: CorsProperties

    @Test
    fun `should split concatenated property to required type`() {
        val allowedOrigins = corsProperties.allowedOrigins
        assertThat(allowedOrigins).containsExactly("http://localhost:aaa.bbb", "http://localhost:ccc.ddd")
    }
}
