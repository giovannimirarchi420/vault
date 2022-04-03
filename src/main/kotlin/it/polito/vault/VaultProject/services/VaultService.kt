package it.polito.vault.VaultProject.services

import it.polito.vault.VaultProject.config.Config
import it.polito.vault.VaultProject.dtos.SecretDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.vault.authentication.TokenAuthentication
import org.springframework.vault.client.VaultEndpoint
import org.springframework.vault.core.VaultTemplate
import java.util.*


@Service
class VaultService(val config: Config) {

    var logger: Logger = LoggerFactory.getLogger(VaultService::class.java)

    fun writeSecret(name: String, secret: SecretDTO): Boolean {
        val endpoint = VaultEndpoint.create("localhost", 8200)
        val path = "polito/$name"
        endpoint.scheme = "http"
        val template = VaultTemplate(endpoint, TokenAuthentication(config.token))

        try {
            logger.info("Attempting to write secret in vault...")
            template.write(path, secret)
            logger.info("Secret written")
        } catch (e: Exception) {
            logger.error("Some error occurred writing a secret")
            return false
        }

        return true
    }

    fun readSecret(pathName: String): SecretDTO? {
        val endpoint = VaultEndpoint.create("localhost", 8200)
        val path = "polito/$pathName"
        endpoint.scheme = "http"
        val template = VaultTemplate(endpoint, TokenAuthentication(config.token))

        try {
            logger.info("Attempting to read secret from vault...")
            val secret = template.read(path, SecretDTO::class.java)?.data
            if(Objects.isNull(secret)){
                throw java.lang.Exception()
            }
            logger.info("Secret read")
            return secret
        } catch (e: Exception) {
            logger.error("Some error occurred reading a secret")
        }
        return null
    }
}