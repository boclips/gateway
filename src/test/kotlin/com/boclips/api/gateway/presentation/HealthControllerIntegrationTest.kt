package com.boclips.api.gateway.presentation

import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.client.getForEntity
import org.springframework.web.client.getForObject

class HealthControllerIntegrationTest : AbstractSpringIntegrationTest() {

    @Test
    fun `health endpoint is available`() {
        val responseEntity = restTemplate.getForEntity("/actuator/health", Map::class.java)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
    }
}