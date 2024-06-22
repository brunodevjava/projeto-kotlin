package br.com.project.kotlin_teste.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordService(@Autowired private val passwordEncoder: PasswordEncoder) {

    fun encodePassword(password: String): String {
        return passwordEncoder.encode(password)
    }

    fun comparePassword(password: String, encodedPassword: String): Boolean {
        return passwordEncoder.matches(password, encodedPassword)
    }
}
