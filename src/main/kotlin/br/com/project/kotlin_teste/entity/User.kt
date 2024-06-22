package br.com.project.kotlin_teste.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.br.CPF
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "usuario")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @field:NotNull(message = "O nome não pode ser nulo")
    @field:Size(min = 1, max = 255, message = "Erro no tamanho de caracteres do nome")
    val name: String,

    @field:NotNull(message = "O email não pode ser nulo")
    @field:Size(min = 1, max = 255, message = "Erro no tamanho de caracteres do email")
    @Email
    val email: String?,

    @field:NotNull(message = "O cpf não pode ser nulo")
    @CPF(message = "CPF inválido!")
    val cpf: String?,


    @field:NotNull(message = "A senha não pode ser nula")
    @field:Size(min = 1, max = 50, message = "Erro no tamanho de caracteres da senha")
    val senha: String?

) : UserDetails {

    override fun getUsername(): String {
        return email ?: ""
    }

    override fun getPassword(): String {
        return senha ?: ""
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        // Aqui você pode retornar as permissões (roles) do usuário, se aplicável
        return mutableListOf()
    }

    override fun isEnabled(): Boolean {
        return true // Altere conforme lógica de habilitação do usuário
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true // Altere conforme lógica de expiração de credenciais
    }

    override fun isAccountNonExpired(): Boolean {
        return true // Altere conforme lógica de expiração de conta
    }

    override fun isAccountNonLocked(): Boolean {
        return true // Altere conforme lógica de conta bloqueada
    }
}
