package com.github.anguzudaniel.profanity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProfanityApplication

fun main(args: Array<String>) {
	runApplication<ProfanityApplication>(*args)
}
