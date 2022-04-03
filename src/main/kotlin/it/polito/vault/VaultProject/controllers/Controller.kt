package it.polito.vault.VaultProject.controllers

import it.polito.vault.VaultProject.dtos.BaseResponse
import it.polito.vault.VaultProject.dtos.SecretDTO
import it.polito.vault.VaultProject.services.VaultService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class Controller(var service: VaultService) {

    @PostMapping("/add/{secretsName}")
    fun addSecret(@PathVariable secretsName: String,
                  @RequestBody secret: SecretDTO
    ) : ResponseEntity<BaseResponse> {
        val response = service.writeSecretWithOperationObject(secretsName, secret)
        val status = if(response.error) HttpStatus.BAD_REQUEST else HttpStatus.OK
        return ResponseEntity(response, status)
    }

    @GetMapping("/get/{pathName}")
    fun readSecret(@PathVariable pathName: String): ResponseEntity<BaseResponse>?{
        val response = service.readSecretWithOperationObject(pathName)
        val status = if(response.error) HttpStatus.BAD_REQUEST else HttpStatus.OK
        return ResponseEntity(response, status)
    }
}