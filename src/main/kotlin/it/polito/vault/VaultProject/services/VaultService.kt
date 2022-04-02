package it.polito.vault.VaultProject.services

import it.polito.vault.VaultProject.config.Config
import it.polito.vault.VaultProject.dtos.SecretDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.vault.authentication.TokenAuthentication
import org.springframework.vault.client.VaultEndpoint
import org.springframework.vault.core.VaultTemplate


@Service
class VaultService(val config: Config) {


    var logger: Logger = LoggerFactory.getLogger(VaultService::class.java)

    fun writeSecret(name: String, secret: SecretDTO): SecretDTO? {
        val endpoint = VaultEndpoint.create("localhost", 8200)
        endpoint.scheme = "http"
        val template = VaultTemplate(endpoint, TokenAuthentication(config.token))
        val path = "polito/$name"
        val vaultWriteResponse = template.write(path, secret)
        logger.info(vaultWriteResponse.toString())
        val response = template.read(path, SecretDTO::class.java)
        return response?.data
    }
}