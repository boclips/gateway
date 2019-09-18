package com.boclips.api.gateway.presentation

import com.boclips.api.gateway.testsupport.AbstractSpringIntegrationTest
import com.jayway.jsonpath.JsonPath
import net.minidev.json.JSONArray
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap

class MetricsControllerIntegrationTest : AbstractSpringIntegrationTest() {

    @Test
    fun `source metrics are captured`() {
        performHttpRequestWithToken(LTI_ACCESS_TOKEN)

        val body = restTemplate.getForObject("/actuator/metrics/boclips.api-usage", String::class.java)

        val count = JsonPath.read<Double>(body, "$.measurements[0].value")
        val urls = JsonPath.read<JSONArray>(body, "$.availableTags[?(@.tag=='url')].values").flatMap { it as Iterable<String> }
        val clientIds = JsonPath.read<JSONArray>(body, "$.availableTags[?(@.tag=='client-id')].values").flatMap { it as Iterable<String> }
        assertThat(count).isEqualTo(1.0)
        assertThat(urls).containsExactly("$gatewayBaseUrl/actuator/health?really=true")
        assertThat(clientIds).containsExactly("lti-pearson-myrealize")
    }

    @Test
    fun `requests are processed normally when token is garbage`() {
        val response = performHttpRequestWithToken(GARBAGE_TOKEN)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `requests are processed normally when missing claim`() {
        val response = performHttpRequestWithToken(TOKEN_WITH_MISSING_CLAIM)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    private fun performHttpRequestWithToken(token: String): ResponseEntity<String> {
        val exchange = restTemplate.exchange(
                "/actuator/health?really=true",
                HttpMethod.GET,
                HttpEntity<Unit>(LinkedMultiValueMap<String, String>().apply {
                    this.add("Authorization", "Bearer $token")
                }),
                String::class.java
        )

        return exchange
    }
}

const val GARBAGE_TOKEN = "OHAIBOCLIPSISTHEBEST"

const val LTI_ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJE" +
        "Vzg1cWVGSGp3dG9UN0Z2RkRwajJWelJyZTJRT3dsc2JPNHQ1blNWQXpFIn0.eyJqdGkiOiI3N" +
        "GI4YWI1MS02ZWE2LTQ1M2MtODRjOC0wMjg4NmUxMjA5OTciLCJleHAiOjE1Njg4MDM5OTUsIm" +
        "5iZiI6MCwiaWF0IjoxNTY4ODAzNjk1LCJpc3MiOiJodHRwczovL2xvZ2luLnN0YWdpbmctYm9" +
        "jbGlwcy5jb20vYXV0aC9yZWFsbXMvYm9jbGlwcyIsImF1ZCI6WyJ2aWRlby1zZXJ2aWNlIiwi" +
        "YWNjb3VudCJdLCJzdWIiOiJhY2ZiMjY3MC1lMTY3LTRlZTEtYjM3NC1lYzhjMzdkZDU3YzAiL" +
        "CJ0eXAiOiJCZWFyZXIiLCJhenAiOiJsdGktcGVhcnNvbi1teXJlYWxpemUiLCJhdXRoX3RpbW" +
        "UiOjAsInNlc3Npb25fc3RhdGUiOiJmM2U5NGI4ZC1jZWVmLTRmMTItOTAwOS1hNWQ0NjQ0NjI" +
        "yZjciLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIlJPTEVfUEVBUlNPTl9N" +
        "WVJFQUxJWkUiXX0sInJlc291cmNlX2FjY2VzcyI6eyJ2aWRlby1zZXJ2aWNlIjp7InJvbGVzI" +
        "jpbIlJPTEVfVklFV19DT0xMRUNUSU9OUyIsIlJPTEVfVklFV19WSURFT1MiXX0sImx0aS1wZW" +
        "Fyc29uLW15cmVhbGl6ZSI6eyJyb2xlcyI6WyJ1bWFfcHJvdGVjdGlvbiJdfSwiYWNjb3VudCI" +
        "6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmll" +
        "dy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiY2xpZW50SG9zdCI6IjEwL" +
        "jE1NC4wLjMiLCJjbGllbnRJZCI6Imx0aS1wZWFyc29uLW15cmVhbGl6ZSIsImVtYWlsX3Zlcm" +
        "lmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LWx0aS1" +
        "wZWFyc29uLW15cmVhbGl6ZSIsImNsaWVudEFkZHJlc3MiOiIxMC4xNTQuMC4zIiwiZW1haWwi" +
        "OiJzZXJ2aWNlLWFjY291bnQtbHRpLXBlYXJzb24tbXlyZWFsaXplQHBsYWNlaG9sZGVyLm9yZ" +
        "yJ9.vaxMAZoGgLra9h1pNJi48C0xroCyC2gtziUA5q8150rd1PhedEykDgL2Tvg4AXVvEAbuh" +
        "cfgU0YbJ4E0Kq90jQqAKQjKtQZULWy4o8I13YZuAWmccdN2V8yKMDR77yjazKsaeIPxeLpiYh" +
        "YtjeDTIBVyU3xgYZPKvzPQRa0o2yeJ4v0Qa0KkFD9tO3v91udIV3ZWRpTq7c6Y4PQUC7Lm0a9" +
        "iS19dusbPNdDva7KyiIK3gYZeXgF8R1YNAkirGSZSdGJZ-MdMgUalPi7KiHNcuOQhKXPRVdUL" +
        "f3JAGEUZdcEpc2K2Rckr0ELbCI0z0L055Dx0__9XrD7oOgdPKSfnGg"

const val TOKEN_WITH_MISSING_CLAIM =
        "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkRXODVxZUZIand0b1Q3RnZGRHBqMl" +
                "Z6UnJlMlFPd2xzYk80dDVuU1ZBekUifQ.eyJqdGkiOiIyZWU0MDM4MS1hOWRhLTQwNjEtOWIyY" +
                "y03YzMzMjc5ZDBlMGIiLCJleHAiOjE1Njg4MDM5MTMsIm5iZiI6MCwiaWF0IjoxNTY4ODAzNjE" +
                "zLCJpc3MiOiJodHRwczovL2xvZ2luLnN0YWdpbmctYm9jbGlwcy5jb20vYXV0aC9yZWFsbXMvY" +
                "m9jbGlwcyIsImF1ZCI6WyJ2aWRlby1zZXJ2aWNlIiwiYWNjb3VudCJdLCJzdWIiOiJjM2M4M2M" +
                "0My1mNTNmLTQyZDEtOTg0YS04YTVhMmQ0NzI1ZjQiLCJ0eXAiOiJCZWFyZXIiLCJhdXRoX3Rpb" +
                "WUiOjAsInNlc3Npb25fc3RhdGUiOiJjNDcyYTM1OC1kMjY3LTRhNzQtODZkYS1jYTM1ODdiYWQ" +
                "zMjgiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHBzOi8vdGVhY2hlcnMuc3RhZ" +
                "2luZy1ib2NsaXBzLmNvbSIsImh0dHA6Ly9sb2NhbGhvc3Q6ODA4MSIsImh0dHA6Ly9ib3RoeS5" +
                "sb2NhbDo4MDgxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJST0xFX0FQSSIsIlJPTEVfQ" +
                "kFDS09GRklDRSIsIlJPTEVfUFVCTElTSEVSIiwiUk9MRV9URUFDSEVSIl19LCJyZXNvdXJjZV9" +
                "hY2Nlc3MiOnsidmlkZW8tc2VydmljZSI6eyJyb2xlcyI6WyJST0xFX0RPV05MT0FEX1RSQU5TQ" +
                "1JJUFQiLCJST0xFX0RFTEVURV9DT0xMRUNUSU9OUyIsIlJPTEVfVVBEQVRFX0NPTExFQ1RJT05" +
                "TIiwiUk9MRV9JTlNFUlRfQ09MTEVDVElPTlMiLCJST0xFX1ZJRVdfQ09MTEVDVElPTlMiLCJST" +
                "0xFX1RBR19WSURFT1MiLCJST0xFX1ZJRVdfQU5ZX1ZJREVPIiwiUk9MRV9WSUVXX0RJU0NJUEx" +
                "JTkVTIiwiUk9MRV9SQVRFX1ZJREVPUyIsIlJPTEVfSU5TRVJUX0VWRU5UUyIsIlJPTEVfVklFV" +
                "19UQUdTIiwiUk9MRV9WSUVXX1ZJREVPUyJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJ2aWV3LXB" +
                "yb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6dHJ1Z" +
                "SwibmFtZSI6Ikpvc2UgQ2FybG9zIFNhbmNoZXoiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJqb3N" +
                "lY2FybG9zQGJvY2xpcHMuY29tIiwiZ2l2ZW5fbmFtZSI6Ikpvc2UgQ2FybG9zIiwiZmFtaWx5X" +
                "25hbWUiOiJTYW5jaGV6IiwiZW1haWwiOiJqb3NlY2FybG9zQGJvY2xpcHMuY29tIn0.PxgP6Lm" +
                "vpeQfiWQph0fGp6bDJ2aJZbMWgywsptExHYnI8yPSLjrkGym5MX6yVY7ipfpWuKyiHDRwn2UHR" +
                "qsGUIpomsVdE8K3fpHq9Mw8F6OZIgRqNxADzuHTtRqnQsAVSXJFmXHW_ezQ-4YlsuHkVTua1DU" +
                "maKWqhl5kmVRIpjE"
