package io.github.yearnlune.wiremockexample.domain

import com.github.tomakehurst.wiremock.WireMockServer
import feign.FeignException
import io.github.yearnlune.wiremockexample.support.WireMockSupport
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@AutoConfigureWireMock
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class ExampleServiceTest @Autowired constructor(
    private val exampleService: ExampleService,
    private val wireMockServer: WireMockServer
) {
    @BeforeAll
    fun setup() {
        WireMockSupport.setupFeignClient(wireMockServer)
    }

    @Test
    @DisplayName("exampleFunctionA-중복된 닉네임이 없을 경우")
    fun exampleFunctionA_succeed() {
        /* GIVEN */
        val nickname = "yearnlune"

        /* WHEN */
        val result = exampleService.exampleFunctionA(nickname)

        /* THEN */
        assertThat(
            result.getOrThrow(),
            `is`("B")
        )
    }

    @Test
    @DisplayName("exampleFunctionA-중복된 닉네임이 있을 경우")
    fun exampleFunctionA_duplicated() {
        /* GIVEN */
        val nickname = "duplicated"

        /* WHEN */
        val result = exampleService.exampleFunctionA(nickname)

        /* THEN */
        assertThat(
            result.getOrThrow(),
            `is`("A")
        )
    }

    @Test
    @DisplayName("exampleFunctionA-오류가 발생한 경우")
    fun exampleFunctionA_exception() {
        /* GIVEN */
        val nickname = "exception"

        /* THEN - EXCEPTED EXCEPTION */
        assertThrows<FeignException.BadRequest> {
            /* WHEN */
            exampleService.exampleFunctionA(nickname).getOrThrow()
        }
    }

    @Test
    @DisplayName("exampleFunctionB")
    fun exampleFunctionB_succeed() {
        /* GIVEN */
        val id = "f0161e50-4428-4e36-a6e0-a35b63cdb3cf"
        val nickname = "yearnlune"

        /* WHEN */
        val result = exampleService.exampleFunctionB(id, nickname)

        /* THEN */
        assertThat(
            result.getOrThrow(),
            `is`(
                AnotherDTO(
                    "f0161e50-4428-4e36-a6e0-a35b63cdb3cf",
                    "raymond",
                    "yearnlune",
                    LocalDateTime.of(2023, 9, 1, 0, 0, 0)
                )
            )
        )
    }

    @Test
    @DisplayName("exampleFunctionC")
    fun exampleFunctionC_succeed() {
        /* GIVEN */
        val another = AnotherDTO(
            name = "raymond",
            nickname = "yearnlune"
        )

        /* WHEN */
        val result = exampleService.exampleFunctionC(another)

        /* THEN */
        assertThat(
            result.getOrThrow(),
            `is`(
                AnotherDTO(
                    "f0161e50-4428-4e36-a6e0-a35b63cdb3cf",
                    "raymond",
                    "yearnlune",
                    LocalDateTime.of(2023, 9, 1, 0, 0, 0)
                )
            )
        )
    }
}