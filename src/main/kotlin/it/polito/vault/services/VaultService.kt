package it.polito.vault.services

import it.polito.vault.constants.VaultConstants
import it.polito.vault.dtos.BaseResponse
import it.polito.vault.dtos.EnablePathRequestDTO
import it.polito.vault.dtos.GenerateTokenRequestDTO
import it.polito.vault.dtos.SecretDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.vault.core.VaultKeyValueOperationsSupport
import org.springframework.vault.core.VaultOperations
import org.springframework.vault.core.VaultTokenOperations
import org.springframework.vault.support.VaultMount
import org.springframework.vault.support.VaultToken
import org.springframework.vault.support.VaultTokenRequest
import java.time.Duration
import java.util.*


@Service
class VaultService(val vaultOperations: VaultOperations) {

    var logger: Logger = LoggerFactory.getLogger(VaultService::class.java)

    fun writeSecret(pathName: String, id: String, secret: SecretDTO) : BaseResponse {

        val response : SecretDTO?
        val keyValueOperations = vaultOperations.opsForKeyValue(pathName, VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)
        return try {
            logger.info("Attempting to write secret in vault...")
            keyValueOperations.put(id, secret)
            response = keyValueOperations.get(id, SecretDTO::class.java)?.data
            logger.info("Secret written")
            BaseResponse(false, response)
        } catch (e: Exception) {
            logger.error("Some error occurred writing a secret")
            BaseResponse(true, e.message)
        }
    }

    fun readSecret(pathName: String, userId: String): BaseResponse {
        val keyValueOperations = vaultOperations.opsForKeyValue(pathName, VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)
        try {
            logger.info("Attempting to read secret from vault...")
            val secret = keyValueOperations.get(userId, SecretDTO::class.java)?.data
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
        return BaseResponse(true, "No secret found in $pathName")
    }

    fun deleteSecret(pathName: String, id: String) : BaseResponse {

        val keyValueOperations = vaultOperations.opsForKeyValue(pathName, VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)
        return try {
            logger.info("Performing secrets delete request for path $pathName/$id.. ")
            keyValueOperations.delete(id)
            logger.info("$pathName/$id secrets deleted")
            BaseResponse(false, "Deleted")
        } catch (e: Exception) {
            logger.info("Error, $pathName/$id secrets NOT deleted")
            BaseResponse(false, "Error, $pathName/$id secrets NOT deleted")
        }
    }

    fun createPath(pathName: String, request: EnablePathRequestDTO) : BaseResponse {
        if(!VaultConstants.secretsTypeList.contains(request.secretsEngineType)){
            return BaseResponse(true, "Secrets engine type not allowed")
        }
        return try {
            vaultOperations.opsForSys().mount(pathName, VaultMount.builder()
                .type(request.secretsEngineType)
                .description(request.description)
                .build())
            BaseResponse(false, "OK")
        } catch ( e: java.lang.Exception) {
            BaseResponse(true, e.message)
        }
    }

    fun generateToken(generateTokenRequestDTO: GenerateTokenRequestDTO) : BaseResponse {

        val tokenRequest = VaultTokenRequest.builder()

        if(Objects.nonNull(generateTokenRequestDTO.renewable))
            generateTokenRequestDTO.renewable?.let { tokenRequest.renewable(it) }
        if(Objects.nonNull(generateTokenRequestDTO.policyName))
            generateTokenRequestDTO.policyName?.let { tokenRequest.withPolicy(it) }
        if(Objects.nonNull(generateTokenRequestDTO.ttl))
            generateTokenRequestDTO.ttl?.let { tokenRequest.ttl(Duration.ofHours(it)) }

        tokenRequest.displayName(generateTokenRequestDTO.displayName)

        val tokenOperations: VaultTokenOperations = vaultOperations.opsForToken()

        return try {
            val tokenResponse = tokenOperations.create(tokenRequest.build())
            BaseResponse(false, tokenResponse.token)
        } catch (e: Exception) {
            BaseResponse(true, e.message)
        }
    }

    fun revokeToken(token: String) : BaseResponse{
        val tokenOperations: VaultTokenOperations = vaultOperations.opsForToken()
        return try {
            tokenOperations.revoke(VaultToken.of(token))
            BaseResponse(false, "Revoked")
        } catch (e: Exception) {
            BaseResponse(true, e.message)
        }
    }
}
