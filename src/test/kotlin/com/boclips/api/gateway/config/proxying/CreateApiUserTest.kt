package com.boclips.api.gateway.config.proxying

import com.boclips.api.gateway.application.user.CreateApiUser
import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.boclips.users.api.factories.OrganisationResourceFactory
import com.boclips.users.api.factories.UserResourceFactory
import feign.FeignException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.lang.IllegalStateException

class CreateApiUserTest : AbstractSpringIntegrationTest() {

    @Autowired
    lateinit var createApiUser: CreateApiUser

    @BeforeEach
    fun before() {
        usersClientFake.clear()
        apiUsersClientFake.clear()
    }

    @Test
    fun `should create api-user`() {
        usersClientFake.add(
            UserResourceFactory.sample(
                id = "cortext-service-account-user-id",
                organisation = OrganisationResourceFactory.sampleDetails(id = "org-id")
            )
        )

        createApiUser(
            serviceAccountUserId = "cortext-service-account-user-id",
            boclipsUserId = "boclips-user-id"
        )

        assertThat(apiUsersClientFake.findAll()).hasSize(1)
        assertThat(apiUsersClientFake.findAll().first().id).isEqualTo("boclips-user-id")
        assertThat(apiUsersClientFake.findAll().first().organisation!!.id).isEqualTo("org-id")
    }

    @Test
    fun `should throw exception when service-account user does not exist`() {
        assertThrows<FeignException.NotFound> {
            createApiUser(
                serviceAccountUserId = "non-existing",
                boclipsUserId = "bo-id"
            )
        }
    }

    @Test
    fun `should throw exception when service account organisation does not exist`() {
        usersClientFake.add(
            UserResourceFactory.sample(
                id = "cortext-service-account-user-id",
                organisation = null
            ))

        assertThrows<IllegalStateException> {
            createApiUser(
                serviceAccountUserId = "cortext-service-account-user-id",
                boclipsUserId = "bo-id"
            )
        }
    }
}