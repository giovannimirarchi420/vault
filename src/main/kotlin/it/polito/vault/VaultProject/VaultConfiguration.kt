package it.polito.vault.VaultProject

import org.springframework.context.annotation.Configuration
import org.springframework.vault.authentication.ClientAuthentication
import org.springframework.vault.authentication.TokenAuthentication
import org.springframework.vault.client.VaultEndpoint
import org.springframework.vault.config.AbstractVaultConfiguration

@Configuration
class VaultConfiguration: AbstractVaultConfiguration() {

    override fun vaultEndpoint(): VaultEndpoint {
        return VaultEndpoint()
    }

    override fun clientAuthentication(): ClientAuthentication {
        return TokenAuthentication("hvs.ZZRbXLnAd1rUS8uFamfsbFJV")
    }

}