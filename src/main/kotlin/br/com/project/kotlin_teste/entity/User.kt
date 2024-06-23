package br.com.project.kotlin_teste.entity

import br.com.project.kotlin_teste.dto.user.UserRegisterDTO
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.validator.constraints.br.CPF
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.OffsetDateTime
import java.time.ZoneId

@Entity
@Table(name = "usuario")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @field:Size(min = 1, max = 255, message = "Erro no tamanho de caracteres do nome")
    var name: String,

    @field:Size(min = 1, max = 255, message = "Erro no tamanho de caracteres do email")
    var email: String,

    @CPF(message = "CPF inválido!")
    var cpf: String,

    @field:Size(min = 1, max = 255, message = "Erro no tamanho de caracteres da senha")
    var senha: String,

    var status: Boolean,

    @CreationTimestamp
    var dataCriacao: OffsetDateTime? = null


) : UserDetails {


    @PrePersist
    fun prePersist() {
        dataCriacao = OffsetDateTime.now(ZoneId.of("America/Sao_Paulo"))
        status = true

    }

    constructor(userRegisterDTO: UserRegisterDTO) : this(
        name = userRegisterDTO.name,
        email = userRegisterDTO.email,
        cpf = userRegisterDTO.cpf,
        senha = userRegisterDTO.senha,
        status = true
    )


    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return listOf(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String {
        return this.senha
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return this.status
    }

}
