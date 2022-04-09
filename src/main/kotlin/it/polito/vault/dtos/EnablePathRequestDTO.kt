package it.polito.vault.dtos

data class EnablePathRequestDTO(val pathName: String, val secretsEngineType: String) {
}