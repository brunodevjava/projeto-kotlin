package br.com.project.kotlin_teste.dto.user

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.br.CPF

data class UserDetailsDTO(

    val name: String,

    val email: String,

    val cpf: String,
)
