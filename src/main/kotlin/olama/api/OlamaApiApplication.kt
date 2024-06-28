package olama.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OlamaApiApplication

fun main(args: Array<String>) {
	runApplication<OlamaApiApplication>(*args)
}
