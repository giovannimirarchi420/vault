package it.polito.vault.controllers

import it.polito.vault.dtos.BaseResponse
import it.polito.vault.dtos.SecretDTO
import it.polito.vault.services.VaultService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class Controller(var service: VaultService) {

    @PostMapping("/{pathName}")
    fun addSecret(@PathVariable pathName: String,
                  @RequestBody secret: SecretDTO
    ) : ResponseEntity<BaseResponse> {
        val response = service.writeSecret(pathName, secret)
        val status = if(response.error) HttpStatus.BAD_REQUEST else HttpStatus.OK
        return ResponseEntity(response, status)
    }

    @GetMapping("/{pathName}")
    fun readSecret(@PathVariable pathName: String): ResponseEntity<BaseResponse> {
        val response = service.readSecret(pathName)
        val status = if(response.error) HttpStatus.BAD_REQUEST else HttpStatus.OK
        return ResponseEntity(response, status)
    }

    @DeleteMapping("/{pathName}")
    fun deleteSecret(@PathVariable pathName: String): ResponseEntity<BaseResponse> {
        val response = service.deleteSecret(pathName)
        val status = if(response.error) HttpStatus.BAD_REQUEST else HttpStatus.OK
        return ResponseEntity(response, status)
    }
}