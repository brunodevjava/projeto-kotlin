package br.com.project.kotlin_teste.dto.auth

import lombok.Data

@Data
data class TokenDetailDTO(
    val token: String,
    val name: String,
    val email: String,
    val status: Boolean,
    val cpf: String,


    ) {
}