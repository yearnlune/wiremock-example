package io.github.yearnlune.wiremockexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class WiremockExampleApplication

fun main(args: Array<String>) {
	runApplication<WiremockExampleApplication>(*args)
}
