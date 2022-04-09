package it.polito.vault.dtos

import java.time.Duration
import java.util.*

data class GenerateTokenRequestDTO(val renewable: Boolean? = null,
                                   val ttl: Long? = null,
                                   val displayName: String = UUID.randomUUID().toString(),
                                   val policyName: String? = null)