package it.polito.vault.VaultProject.controllers

import it.polito.vault.VaultProject.dtos.BaseResponse
import it.polito.vault.VaultProject.dtos.SecretDTO
import it.polito.vault.VaultProject.services.VaultService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import java.util.*

@RestController
class Controller(var service: VaultService) {

    @PostMapping("/add/{secretsName}")
    fun addSecret(@PathVariable secretsName: String,
                  @RequestBody secret: SecretDTO
    ) : ResponseEntity<BaseResponse> {
        try {
            var response = service.writeSecret(secretsName, secret)
            if(Objects.isNull(response))
                return ResponseEntity(BaseResponse(400, "And error occurred reading"), HttpStatus.INTERNAL_SERVER_ERROR)
            return ResponseEntity(BaseResponse(201, response.toString()), HttpStatus.OK)
        } catch (excepion: Exception) {
            return ResponseEntity(BaseResponse(400, excepion.message.toString()), HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(BaseResponse(400, "ERROR"), HttpStatus.BAD_REQUEST)
    }
}