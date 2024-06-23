package br.com.project.kotlin_teste.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
class SecurityConfig(
    private val securityFilter: SecurityFilter
) {
    @Bean
    @Throws(Exception::class)
    protected fun configure(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .cors { cors: CorsConfigurer<HttpSecurity?> ->
                cors.configurationSource { request: HttpServletRequest? ->
                    val config = CorsConfiguration()
                    // Configurar as políticas de CORS aqui
                    config.addAllowedOrigin("*") // Permitir solicitações de todos os origens
                    config.addAllowedMethod("*") // Permitir todos os métodos HTTP
                    config.addAllowedHeader("*")
                    config.addExposedHeader("*")
                    config
                }
            }
            .sessionManagement { sm: SessionManagementConfigurer<HttpSecurity?> ->
                sm.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/login/auth",
                        "/usuario",
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(this.securityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
