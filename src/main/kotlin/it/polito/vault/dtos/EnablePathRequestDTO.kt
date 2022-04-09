package it.polito.vault.dtos


data class EnablePathRequestDTO(val secretsEngineType: String, val description: String = "") {
}