package com.boclips.api.gateway.domain.model

import com.boclips.api.gateway.domain.model.Link
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LinkTest {

    @Test
    fun `replaceHost when no port sets provided host`() {
        val link = Link(href = "http://example.com/good/luck", rel = "luck", templated = true)

        val newLink = link.replaceDomain("http://new.host")

        assertThat(newLink.href).isEqualTo("http://new.host/good/luck")
        assertThat(newLink.rel).isEqualTo("luck")
        assertThat(newLink.templated).isEqualTo(true)
    }

    @Test
    fun `replaceHost when host with port sets provided host`() {
        val link = Link(href = "http://example.com:8085/good/luck", rel = "luck", templated = true)

        val newLink = link.replaceDomain("http://new.host")

        assertThat(newLink.href).isEqualTo("http://new.host/good/luck")
        assertThat(newLink.rel).isEqualTo("luck")
        assertThat(newLink.templated).isEqualTo(true)
    }
}