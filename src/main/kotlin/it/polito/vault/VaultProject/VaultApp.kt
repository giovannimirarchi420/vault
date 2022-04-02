package it.polito.vault.VaultProject

import org.springframework.vault.core.VaultOperations

class VaultApp(var vaultOperations: VaultOperations) {

    fun writeSecret(userId: String, password: String) {
        var dataMap = HashMap<String, String>()
        dataMap.put("password", password)
        vaultOperations.write(userId, dataMap)
    }

    fun readSecret(userId: String): String {
        var response = vaultOperations.read(userId)
        return response.toString()
    }

}