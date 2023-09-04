package io.github.yearnlune.wiremockexample.support

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalToJson
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.matching
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.put
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import io.github.yearnlune.wiremockexample.domain.AnotherDTO
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class WireMockSupport {

    companion object {
        private const val ANOTHER_BASE_URL = "/api/another"
        private val objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

        fun setupFeignClient(mockServer: WireMockServer) {
            setupAnotherClient(mockServer)
        }

        private fun setupAnotherClient(mockServer: WireMockServer) {
            mockServer.stubFor(
                get(urlPathMatching("$ANOTHER_BASE_URL/nickname/duplicated$"))
                    .atPriority(1)
                    .willReturn(
                        aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withHeader("Content-Type", "application/json")
                            .withBody(objectMapper.writeValueAsString(true))
                    )
            )
            mockServer.stubFor(
                get(urlPathMatching("$ANOTHER_BASE_URL/nickname/exception$"))
                    .atPriority(1)
                    .willReturn(
                        aResponse()
                            .withStatus(HttpStatus.BAD_REQUEST.value())
                            .withHeader("Content-Type", "application/json")
                    )
            )
            mockServer.stubFor(
                get(urlPathMatching("$ANOTHER_BASE_URL/nickname/\\w{4,12}$"))
                    .atPriority(999)
                    .willReturn(
                        aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withHeader("Content-Type", "application/json")
                            .withBody(objectMapper.writeValueAsString(false))
                    )
            )
            mockServer.stubFor(
                put(urlPathMatching("$ANOTHER_BASE_URL/f0161e50-4428-4e36-a6e0-a35b63cdb3cf"))
                    .atPriority(1)
                    .withQueryParam("nickname", matching(".*"))
                    .willReturn(
                        aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withHeader("Content-Type", "application/json")
                            .withBody(
                                objectMapper.writeValueAsString(
                                    AnotherDTO(
                                        "f0161e50-4428-4e36-a6e0-a35b63cdb3cf",
                                        "raymond",
                                        "yearnlune",
                                        LocalDateTime.of(2023, 9, 1, 0, 0, 0)
                                    )
                                )
                            )
                    )
            )
            mockServer.stubFor(
                post(urlPathMatching(ANOTHER_BASE_URL))
                    .atPriority(1)
                    .withRequestBody(
                        equalToJson(
                            objectMapper.writeValueAsString(
                                AnotherDTO(
                                    name = "raymond",
                                    nickname = "yearnlune"
                                )
                            )
                        )
                    )
                    .willReturn(
                        aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withHeader("Content-Type", "application/json")
                            .withBody(
                                objectMapper.writeValueAsString(
                                    AnotherDTO(
                                        "f0161e50-4428-4e36-a6e0-a35b63cdb3cf",
                                        "raymond",
                                        "yearnlune",
                                        LocalDateTime.of(2023, 9, 1, 0, 0, 0)
                                    )
                                )
                            )
                    )
            )
        }
    }
}