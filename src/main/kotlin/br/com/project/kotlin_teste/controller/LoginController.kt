package br.com.project.kotlin_teste.controller

import br.com.project.kotlin_teste.security.SecurityService
import br.com.project.kotlin_teste.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(
    private val authManager: AuthenticationManager,
    private val securityService: SecurityService
) {

}