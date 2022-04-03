package it.polito.vault.services

import it.polito.vault.dtos.BaseResponse
import it.polito.vault.dtos.SecretDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.vault.core.VaultKeyValueOperationsSupport
import org.springframework.vault.core.VaultOperations
import java.lang.NullPointerException
import java.util.*


@Service
class VaultService(val vaultOperations: VaultOperations) {

    var logger: Logger = LoggerFactory.getLogger(VaultService::class.java)

    fun writeSecret(name: String, secret: SecretDTO) : BaseResponse {
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

    fun readSecret(pathName: String): BaseResponse {
        val path = "polito/$pathName"
        val keyValueOperations = vaultOperations.opsForKeyValue(path, VaultKeyValueOperationsSupport.KeyValueBackend.KV_1)

        try {
            logger.info("Attempting to read secret from vault...")
            val secret = keyValueOperations.get(path, SecretDTO::class.java)?.data
            if(Objects.isNull(secret)){
                throw NullPointerException()
            }
            logger.info("Secret read")
            return BaseResponse(false, secret)
        } catch (e: NullPointerException) {
            logger.error("Secret not found")
        } catch (e: Exception) {
            logger.error("Some error occurred reading a secret")
        }
        return BaseResponse(true, "No secret found in $path")
    }

    fun deleteSecret(pathName: String) : BaseResponse {
        val path = "polito/$pathName"
        val keyValueOperations = vaultOperations.opsForKeyValue(path, VaultKeyValueOperationsSupport.KeyValueBackend.KV_1)
        try {
            logger.info("Performing secrets delete request for path $path.. ")
            keyValueOperations.delete(path)
            logger.info("$path secrets deleted")
            return BaseResponse(false, "Deleted")
        } catch (e: Exception) {
            logger.info("Error, $path secrets NOT deleted")
            return BaseResponse(false, "Error, $path secrets NOT deleted")
        }
    }
}
