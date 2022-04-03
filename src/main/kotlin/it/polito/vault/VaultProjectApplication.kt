package it.polito.vault

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VaultProjectApplication

fun main(args: Array<String>) {
	runApplication<VaultProjectApplication>(*args)
}
