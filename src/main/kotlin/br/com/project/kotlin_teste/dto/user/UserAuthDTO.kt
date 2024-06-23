package br.com.project.kotlin_teste.dto.user

import jakarta.validation.constraints.NotBlank
import lombok.Data

@Data
data class UserAuthDTO(


    @NotBlank(message = "Email é obrigatório")
    val email: String,

    @NotBlank(message = "Senha é obrigatória")
    val senha: String
) {
}