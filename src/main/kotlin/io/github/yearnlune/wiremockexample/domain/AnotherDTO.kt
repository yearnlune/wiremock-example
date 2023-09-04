package io.github.yearnlune.wiremockexample.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class AnotherDTO (
    val id: String,
    val name: String,
    val nickname: String,
    @JsonProperty val createdAt: LocalDateTime
)