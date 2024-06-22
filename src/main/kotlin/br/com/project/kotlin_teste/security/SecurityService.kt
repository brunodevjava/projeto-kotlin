package br.com.project.kotlin_teste.security

import br.com.project.kotlin_teste.dto.VerificaTokenDTO
import br.com.project.kotlin_teste.repository.UserRepository
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.interfaces.DecodedJWT
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

@Service
class SecurityService @Autowired constructor(
    private val env: Environment,
    private val userRepository: UserRepository
) {

    fun generateToken(email: String): String {
        try {
            val algorithm = getAlgorithm()
            return JWT.create()
                .withIssuer("TESTE-KOTLIN")
                .withSubject(email)
                .withExpiresAt(getTokenExpirationTime())
                .sign(algorithm)
        } catch (exception: JWTCreationException) {
            throw RuntimeException(exception)
        }
    }

    private fun getTokenExpirationTime(): Date {
        val expirationInterval = env.getProperty("security.token-expiration")?.toInt() ?: 24 // default 24 hours
        return Date.from(Instant.now().plusSeconds(expirationInterval.toLong() * 3600))
    }

    private fun getRequestToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        return authHeader?.replace("Bearer ", "")
    }

    private fun getSubject(token: String): String {
        val algorithm = getAlgorithm()
        return JWT.require(algorithm)
            .withIssuer("TESTE-KOTLIN")
            .build()
            .verify(token)
            .subject
    }

    private fun getAlgorithm(): Algorithm {
        return Algorithm.HMAC256(env.getProperty("security.hmac-secret") ?: "default-secret")
    }

    @Throws(SecurityException::class)
    fun authenticateRequest(request: HttpServletRequest) {
        val token = getRequestToken(request) ?: return
        val subject = getSubject(token)
        val user = userRepository.findByEmail(subject) ?: throw SecurityException("User not found for email: $subject")
        val authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
        SecurityContextHolder.getContext().authentication = authentication
    }

    fun validateToken(token: VerificaTokenDTO): ResponseEntity<String> {
        try {
            val decodedJWT: DecodedJWT = JWT.decode(token.token)
            if (decodedJWT.expiresAt.after(Date())) {
                return ResponseEntity.ok("O token ainda não expirou.")
            } else {
                val expirationDateFormatted = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(decodedJWT.expiresAt)
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("O token expirou em: $expirationDateFormatted")
            }
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body("O token é inválido.")
        }
    }

}