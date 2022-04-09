package it.polito.vault.controllers

import it.polito.vault.dtos.BaseResponse
import it.polito.vault.dtos.EnablePathRequestDTO
import it.polito.vault.dtos.GenerateTokenRequestDTO
import it.polito.vault.dtos.SecretDTO
import it.polito.vault.services.VaultService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class Controller(var service: VaultService) {

    @PostMapping("/kv/{pathName}")
    fun addSecret(@PathVariable pathName: String,
                  @RequestBody secret: SecretDTO
    ) : ResponseEntity<BaseResponse> {
        val response = service.writeSecret(pathName, secret)
        val status = if(response.error) HttpStatus.BAD_REQUEST else HttpStatus.OK
        return ResponseEntity(response, status)
    }

    @GetMapping("/kv/{pathName}")
    fun readSecret(@PathVariable pathName: String): ResponseEntity<BaseResponse> {
        val response = service.readSecret(pathName)
        val status = if(response.error) HttpStatus.BAD_REQUEST else HttpStatus.OK
        return ResponseEntity(response, status)
    }

    @DeleteMapping("/kv/{pathName}")
    fun deleteSecret(@PathVariable pathName: String): ResponseEntity<BaseResponse> {
        val response = service.deleteSecret(pathName)
        val status = if(response.error) HttpStatus.BAD_REQUEST else HttpStatus.OK
        return ResponseEntity(response, status)
    }

    @PostMapping("/enable")
    fun enablePath(@RequestBody request: EnablePathRequestDTO) : ResponseEntity<BaseResponse> {
        val response = service.createPath(request)
        val status = if(response.error) HttpStatus.BAD_REQUEST else HttpStatus.OK
        return ResponseEntity(response, status)
    }

    @GetMapping("/generate/{pathName}")
    fun generateKeys(@PathVariable pathName: String) : ResponseEntity<BaseResponse> {
        val response = service.generateKeys(pathName)
        val status = if(response.error) HttpStatus.BAD_REQUEST else HttpStatus.OK
        return ResponseEntity(response, status)
    }

    @GetMapping("/token")
    fun generateToken(@RequestBody generateTokenRequestDTO: GenerateTokenRequestDTO) : ResponseEntity<BaseResponse> {
        val response = service.generateToken(generateTokenRequestDTO)
        val status = if(response.error) HttpStatus.BAD_REQUEST else HttpStatus.OK
        return ResponseEntity(response, status)
    }

    @DeleteMapping("/token/revoke/{token}")
    fun revokeToken(@PathVariable token: String) : ResponseEntity<BaseResponse> {
        val response = service.revokeToken(token)
        val status = if(response.error) HttpStatus.BAD_REQUEST else HttpStatus.OK
        return ResponseEntity(response, status)
    }
}