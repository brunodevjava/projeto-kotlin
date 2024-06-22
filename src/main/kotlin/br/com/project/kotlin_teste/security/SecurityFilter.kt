package br.com.project.kotlin_teste.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*

@Component
class SecurityFilter @Autowired constructor(private val securityService: SecurityService) : OncePerRequestFilter() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            securityService.authenticateRequest(request)
            liberacaoCors(response) // Liberar CORS antes de continuar
            filterChain.doFilter(request, response)
        } catch (e: SecurityException) {
            handleSecurityException(response, e)
        } catch (e: Exception) {
            println("ERROR: ${e.message}")
            handleGenericException(response, e)
        }
    }

    @Throws(IOException::class)
    private fun handleSecurityException(response: HttpServletResponse, e: SecurityException) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        e.message?.let { response.writer.write(it) }
    }

    @Throws(IOException::class)
    private fun handleGenericException(response: HttpServletResponse, e: Exception) {
        response.status = 500
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.contentType = "application/json"
        response.writer.append(json(e.message))
        println("ERRO: ${e.message}")
    }

    private fun liberacaoCors(response: HttpServletResponse) {
        if (response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*")
        }

        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.setHeader("Access-Control-Allow-Headers", "*")
        }

        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*")
        }

        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.setHeader("Access-Control-Allow-Methods", "*")
        }

        if (response.getHeader("Access-Control-Allow-Credentials") == null) {
            response.setHeader("Access-Control-Allow-Credentials", "true")
        }
    }

    private fun json(message: String?): String {
        val date = Date().time
        return "{\"timestamp\": $date," +
                "\"status\": 401, " +
                "\"error\": \"Unauthorized\", " +
                "\"message\": \"${message?.replace("\"", " ")}\", " +
                "\"path\": \"/login\"}"
    }
}
