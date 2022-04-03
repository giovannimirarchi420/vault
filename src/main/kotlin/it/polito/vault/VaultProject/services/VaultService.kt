package it.polito.vault.VaultProject.services

import it.polito.vault.VaultProject.dtos.BaseResponse
import it.polito.vault.VaultProject.dtos.SecretDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.vault.core.VaultKeyValueOperationsSupport
import org.springframework.vault.core.VaultOperations
import java.util.*


@Service
class VaultService(val vaultOperations: VaultOperations) {

    var logger: Logger = LoggerFactory.getLogger(VaultService::class.java)

    fun writeSecretWithOperationObject(name: String, secret: SecretDTO) : BaseResponse {
        val path = "polito/$name"
        val keyValueOperations = vaultOperations.opsForKeyValue(path, VaultKeyValueOperationsSupport.KeyValueBackend.KV_1)
        try {
            logger.info("Attempting to write secret in vault...")
            keyValueOperations.put(path, secret)
            logger.info("Secret written")
        } catch (e: Exception) {
            logger.error("Some error occurred writing a secret")
            return BaseResponse(true, "Error, secret not created")
        }

        return BaseResponse(false, "Secret created")
    }

    fun readSecretWithOperationObject(pathName: String): BaseResponse {
        val path = "polito/$pathName"
        val keyValueOperations = vaultOperations.opsForKeyValue(path, VaultKeyValueOperationsSupport.KeyValueBackend.KV_1)

        try {
            logger.info("Attempting to read secret from vault...")
            val secret = keyValueOperations.get(path, SecretDTO::class.java)?.data
            if(Objects.isNull(secret)){
                throw java.lang.Exception()
            }
            logger.info("Secret read")
            return BaseResponse(false, secret)
        } catch (e: Exception) {
            logger.error("Some error occurred reading a secret")
        }
        return BaseResponse(true, "No secret found in $path")
    }
}
