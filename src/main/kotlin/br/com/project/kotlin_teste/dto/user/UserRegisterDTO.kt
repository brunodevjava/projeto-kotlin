package br.com.project.kotlin_teste.dto.user

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.br.CPF

data class UserRegisterDTO(

    @NotBlank(message = "Nome é obrigatório")
    val name: String,

    @NotBlank(message = "Email é obrigatório")
    val email: String,

    @CPF(message = "CPF inválido")
    val cpf: String,

    @NotBlank(message = "Senha é obrigatória")
    val senha: String

)
