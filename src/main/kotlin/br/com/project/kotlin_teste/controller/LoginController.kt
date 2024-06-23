package br.com.project.kotlin_teste.controller

import br.com.project.kotlin_teste.dto.auth.TokenDetailDTO
import br.com.project.kotlin_teste.dto.user.UserAuthDTO
import br.com.project.kotlin_teste.entity.User
import br.com.project.kotlin_teste.security.SecurityService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(
    private val authManager: AuthenticationManager,
    private val securityService: SecurityService
) {


    @PostMapping("/auth")
    fun login(@RequestBody authDTO: @Valid UserAuthDTO): ResponseEntity<TokenDetailDTO>? {
        try {
            val token = UsernamePasswordAuthenticationToken(authDTO.email, authDTO.senha)
            val auth = authManager.authenticate(token)
            val user: User = auth.principal as User
            val userToken: String = securityService.generateToken(user.email)
            return ResponseEntity.ok(
                TokenDetailDTO(
                    userToken,
                    user.name,
                    user.email,
                    user.status,
                    user.cpf
                )
            )
        } catch (exception: Exception) {
            println("ERROR: ${exception.message}")
            exception.printStackTrace() // Print the stack trace for more detailed error information
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }
    }




}