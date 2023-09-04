package io.github.yearnlune.wiremockexample.domain

import java.time.LocalDateTime

data class AnotherDTO(
    val id: String,
    val name: String,
    val nickname: String,
    val createdAt: LocalDateTime
)