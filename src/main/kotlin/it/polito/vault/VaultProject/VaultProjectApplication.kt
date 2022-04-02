package it.polito.vault.VaultProject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.vault.core.VaultTemplate

@SpringBootApplication
class VaultProjectApplication

fun main(args: Array<String>) {
	var context = runApplication<VaultProjectApplication>(*args)
	//var vault = context.getBean(VaultTemplate::class.java)
}
