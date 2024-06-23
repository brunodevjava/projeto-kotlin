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
class SecurityFilter @Autowired constructor(
    private val securityService: SecurityService
) : OncePerRequestFilter() {

    @Throws(IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            // Authenticate request using SecurityService
            securityService.authenticateRequest(request)

            // Handle CORS
            liberacaoCors(response)

            // Proceed with the filter chain
            filterChain.doFilter(request, response)
        } catch (e: SecurityException) {
            handleSecurityException(response, e)
        } catch (e: Exception) {
            handleGenericException(response, e)
        }
    }

    private fun handleSecurityException(response: HttpServletResponse, e: SecurityException) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write(e.message ?: "Unauthorized")
    }

    private fun handleGenericException(response: HttpServletResponse, e: Exception) {
        response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.contentType = "application/json"
        response.writer.append(json(e.message))
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
        return "{\"timestamp\": $date, \"status\": 401, \"error\": \"Unauthorized\", \"message\": \"${message?.replace("\"", " ")}\", \"path\": \"/login\"}"
    }
}
