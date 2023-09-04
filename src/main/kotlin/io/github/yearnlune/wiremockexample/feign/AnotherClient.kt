package io.github.yearnlune.wiremockexample.feign

import io.github.yearnlune.wiremockexample.domain.AnotherDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "another",
    url = "\${app.another.url}"
)
interface AnotherClient {

    @GetMapping("/api/nickname/{nickname}")
    fun hasNickname(@PathVariable("nickname") nickname: String): Boolean

    @GetMapping("/api/{id}")
    fun findAnotherById(@PathVariable("id") id: String): AnotherDTO

    @PutMapping("/api/{id}")
    fun updateNickname(@PathVariable("id") id: String, @RequestParam("nickname") nickname: String): AnotherDTO
}