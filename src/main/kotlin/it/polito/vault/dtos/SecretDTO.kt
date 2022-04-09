package it.polito.vault.dtos

import java.util.*

data class SecretDTO(var id: String = UUID.randomUUID().toString(), var password: String = "")