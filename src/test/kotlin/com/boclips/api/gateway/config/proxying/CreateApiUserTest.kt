package com.boclips.api.gateway.config.proxying

import com.boclips.api.gateway.application.user.CreateApiUser
import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.boclips.users.api.factories.OrganisationResourceFactory
import com.boclips.users.api.factories.UserResourceFactory
import com.boclips.users.api.httpclient.test.fakes.UsersClientFake
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import feign.FeignException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class CreateApiUserTest : AbstractSpringIntegrationTest() {

    @Autowired
    lateinit var createApiUser: CreateApiUser

    lateinit var spyUsersClientFake: UsersClientFake

    @BeforeEach
    fun before() {
        usersClientFake.clear()
        spyUsersClientFake = spy(usersClientFake)
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
            boclipsUserId = "boclips-user-id",
            externalUserId = "external-user-id"
        )

        val apiUser = usersClientFake.getUser("boclips-user-id")
        assertThat(apiUser).isNotNull
        assertThat(apiUser.id).isEqualTo("boclips-user-id")
        assertThat(apiUser.organisation!!.id).isEqualTo("org-id")
    }

    @Test
    fun `should not invoke further calls to create api user when that user already exists`() {
        usersClientFake.add(
            UserResourceFactory.sample(
                id = "boclips-user-id",
            )
        )

        createApiUser(
            serviceAccountUserId = "cortext-service-account-user-id",
            boclipsUserId = "boclips-user-id",
            externalUserId = "external-user-id"
        )

        verify(spyUsersClientFake, times(0)).getUser(any())
        verify(spyUsersClientFake, times(0)).createApiUser(any())
    }

    @Test
    fun `should throw exception when service-account user does not exist`() {
        assertThrows<FeignException.NotFound> {
            createApiUser(
                serviceAccountUserId = "non-existing",
                boclipsUserId = "bo-id",
                externalUserId = "external-user-id"
            )
        }
    }

    @Test
    fun `should throw exception when service account organisation does not exist`() {
        usersClientFake.add(
            UserResourceFactory.sample(
                id = "cortext-service-account-user-id",
                organisation = null
            )
        )

        assertThrows<IllegalStateException> {
            createApiUser(
                serviceAccountUserId = "cortext-service-account-user-id",
                boclipsUserId = "bo-id",
                externalUserId = "external-user-id"
            )
        }
    }
}
