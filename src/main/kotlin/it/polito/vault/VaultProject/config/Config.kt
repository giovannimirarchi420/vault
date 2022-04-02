package it.polito.vault.VaultProject.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.logging.LogLevel
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
class Config() {

    @Value("\${config.vault.token}")
    var token: String = ""
}