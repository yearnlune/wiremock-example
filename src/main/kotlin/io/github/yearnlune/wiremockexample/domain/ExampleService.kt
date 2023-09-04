package io.github.yearnlune.wiremockexample.domain

import io.github.yearnlune.wiremockexample.feign.AnotherClient
import org.springframework.stereotype.Service

@Service
class ExampleService(
    private val anotherClient: AnotherClient
) {

    fun exampleFunctionA(nickname: String): Result<String> {
        return runCatching {
            /* Some codes... */
            if (anotherClient.hasNickname(nickname)) {
                "A"
            } else {
                "B"
            }
        }
    }

    fun exampleFunctionB(id: String, nickname: String): Result<AnotherDTO> {
        return runCatching {
            /* Some codes... */
            anotherClient.updateNickname(id, nickname)
        }
    }
}