package it.polito.vault.VaultProject.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.vault.authentication.ClientAuthentication
import org.springframework.vault.authentication.TokenAuthentication
import org.springframework.vault.client.VaultEndpoint
import org.springframework.vault.config.AbstractVaultConfiguration


@Configuration
class VaultConfig: AbstractVaultConfiguration() {


    @Value("\${config.vault.host}")
    var vaultHost: String = "localhost"


    @Value("\${config.vault.port}")
    var vaultPort: Int = 8200


    @Value("\${config.vault.token}")
    var token: String = ""

    /**
     * Return us implementation of VaultEndpoint.
     */
    override fun vaultEndpoint(): VaultEndpoint {
        val endpoint = VaultEndpoint.create(vaultHost, vaultPort)
        endpoint.scheme = "http"
        return endpoint
    }

    /**
     * Configure a Token authentication.
     */
    override fun clientAuthentication(): ClientAuthentication {
        return TokenAuthentication(token)
    }

}